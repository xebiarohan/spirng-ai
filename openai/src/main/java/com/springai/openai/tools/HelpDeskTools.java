package com.springai.openai.tools;

import com.springai.openai.entity.HelpDeskTicket;
import com.springai.openai.model.TicketRequest;
import com.springai.openai.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpDeskTools.class);

    private final HelpDeskTicketService helpDeskTicketService;


//    @Tool(name = "createTicket", description = "Creates a support Ticket")
//    String createTicket(@ToolParam(description = "Details to create a support ticket") TicketRequest ticketRequest,
//                        ToolContext toolContext) {
//        String username = (String) toolContext.getContext().get("username");
//        LOGGER.info("Creating support ticket for user: {} with details: {}", username, ticketRequest);
//        HelpDeskTicket savedTicket = helpDeskTicketService.createTicket(ticketRequest,username);
//        LOGGER.info("Ticket created successfully. Ticket ID: {}, Username: {}", savedTicket.getId(), savedTicket.getUsername());
//        return "Ticket #" + savedTicket.getId() + " created successfully for user " + savedTicket.getUsername();
//    }

    @Tool(name = "createTicket", description = "Creates a support Ticket", returnDirect = true)
    String createTicket(@ToolParam(description = "Details to create a support ticket") TicketRequest ticketRequest,
                        ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        LOGGER.info("Creating support ticket for user: {} with details: {}", username, ticketRequest);
        HelpDeskTicket savedTicket = helpDeskTicketService.createTicket(ticketRequest,username);
        LOGGER.info("Ticket created successfully. Ticket ID: {}, Username: {}", savedTicket.getId(), savedTicket.getUsername());
        return "Ticket #" + savedTicket.getId() + " created successfully for user " + savedTicket.getUsername();
    }

    @Tool(description = "Fetch the status of the tickets based on a given username")
    List<HelpDeskTicket> getTicketStatus(ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        LOGGER.info("Fetching tickets for user: {}", username);
        List<HelpDeskTicket> tickets =  helpDeskTicketService.getTicketsByUsername(username);
        LOGGER.info("Found {} tickets for user: {}", tickets.size(), username);
        // throw new RuntimeException("Unable to fetch ticket status");
        return tickets;
    }

}
