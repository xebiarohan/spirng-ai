package com.springai.mcpserverremote.tools;

import com.springai.mcpserverremote.entity.HelpDeskTicket;
import com.springai.mcpserverremote.model.TicketRequest;
import com.springai.mcpserverremote.service.HelpDeskTicketService;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.ai.mcp.annotation.context.McpSyncRequestContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpDeskTools.class);

    private final HelpDeskTicketService helpDeskTicketService;


    @McpTool(name = "createTicket", description = "Creates a support Ticket")
    String createTicket(@McpToolParam(description = "Details to create a support ticket") TicketRequest ticketRequest,
                        ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        LOGGER.info("Creating support ticket for user: {} with details: {}", username, ticketRequest);
        HelpDeskTicket savedTicket = helpDeskTicketService.createTicket(ticketRequest,username);
        LOGGER.info("Ticket created successfully. Ticket ID: {}, Username: {}", savedTicket.getId(), savedTicket.getUsername());
        return "Ticket #" + savedTicket.getId() + " created successfully for user " + savedTicket.getUsername();
    }

    @McpTool(name = "getTicketStatus", description = "Fetch the status of the tickets based on a given username")
    List<HelpDeskTicket> getTicketStatus(ToolContext toolContext, McpSyncRequestContext context) {
        String username = (String) toolContext.getContext().get("username");
        // sending logs to MCP client
        context.info("Fetching tickets for user: " + username);
        List<HelpDeskTicket> tickets =  helpDeskTicketService.getTicketsByUsername(username);
        LOGGER.info("Found {} tickets for user: {}", tickets.size(), username);

        // throw new RuntimeException("Unable to fetch ticket status");
        return tickets;
    }

    @McpTool(name = "summarizeTickets", description = "Generate a friendly, natural-language summary of all the " +
            "support tickets that belong to a given username")
    String summarizeTickets(@McpToolParam(description = "Username to summarize the help desk tickets for")
                            String username, McpSyncRequestContext ctx) {
        LOGGER.info("Generating ticket summary for user: {}", username);
        List<HelpDeskTicket> tickets = helpDeskTicketService.getTicketsByUsername(username);

        if (tickets.isEmpty()) {
            return "No support tickets were found for user " + username + ".";
        }

        // MCP Sampling lets this server ask the connected client to run an LLM
        // completion on its behalf. First make sure the client actually advertised
        // the sampling capability during initialization.
        if (!ctx.sampleEnabled()) {
            LOGGER.warn("Connected MCP client does not support sampling. Returning raw ticket data instead.");
            return tickets.toString();
        }

        String ticketData = tickets.stream()
                .map(t -> "Ticket #" + t.getId() + " | Issue: " + t.getIssue()
                        + " | Status: " + t.getStatus() + " | ETA: " + t.getEta())
                .collect(Collectors.joining("\n"));

        String systemPrompt = """
                You are a friendly help desk assistant. Using ONLY the ticket data provided by the user,
                write a short, warm summary for the customer about the status of their support tickets.
                Mention how many tickets they have in total, group them by status (OPEN, IN_PROGRESS, CLOSED),
                and reassure them about the ones that are still being worked on. Keep it under 120 words and
                do not invent any information that is not present in the ticket data.
                """;

        LOGGER.info("Requesting LLM completion from the MCP client via sampling...");
        ctx.info("Asking your AI assistant to summarize " + tickets.size() + " ticket(s) for " + username);

        McpSchema.CreateMessageResult result = ctx.sample(spec -> spec
                .systemPrompt(systemPrompt)
                .message("Here are the support tickets for " + username + ":\n" + ticketData));

        String summary = ((McpSchema.TextContent) result.content()).text();
        LOGGER.info("Sampling response received. Model used by client: {}", result.model());
        return summary;
    }

}
