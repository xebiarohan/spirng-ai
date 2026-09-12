Spring AI

-  Framework for integrating AI into spring application

## Chat client

## Chat model

## Docker model runner

## Message roles
    - user
    - System
    - function/tools
    - output

## Defaults
    - preconfigured values or behaviors that are applied automatically to each requests

## prompt template

## prompt stuffing

## Advisors
    - interseptors or middleware for prompt flow

## Chat options
    - model, frequencyPenalty, presencePenalty, temprature, topP, stopSequence, maxTokens, topK

## response types
    - content(), entity(), chatResponse(), chatClientResponse()

## different response format
    - string, json, XML,  POJOs, ListOutputConverter, MapOutputConverter,  BeanOutputConverter

## GenAI : 
    types of models : LLM and Diffusion

## LLM models
    - embeddings
    - static embeddings and positional embeddings
    - attention layer of the transformer

## ChatMemory and ChatMemoryRepository

## RAG
    - Retrieval Augmented generation
        - Retrieval - Searching the document in Vector database
        - Augmentation - Picks the most relevent documents
        - Generation - Generate the answer based on the prompt and retrieval knowledge

## Vector database
    - Stores semantic meaning of text, audio, video, documents, etc in numeric multi dimentioanal vector format.
    - Popular vector store tools : cassandra, ElasticSearch, MongoDB, PostgreSQL with pgvector extension, Pinecone, Qdrant etc
    - SearchRequest to search documents
    - Qdrant dashboard : http://localhost:6333/dashboard

## Apache Tika 
    - reading text from different types of files like PDF

## RetrievalAugmentationAdvisor

## Advance RAG
    - Pre retrieval using queryTransformers
    - Post retrieval using documentPostProcessors

## Embedding models used by Vector store to create embeddings of documents to store

## Semantic caching
    - using redis client

## Tools calling
    - Retrieval information : fetching real time data
    - Taking action : Set an alarm, book a flight ticket, etc

## @Tool annotation
    - ToolParam
    - ToolContext
    - ToolExecutionException

## Finish reason
    - it is a value returned with the assistant message to the Spring AI application
    - STOP, TOOL_CALLS

## ChatClientBuilderCustomizer
    - @ConditionalOnProperty

## ToolExecutionExceptionProcessor

## LLM models
    - LLM model only
    - LLM with RAG
    - LLM with tools
    - AI Agent
    - Agentic AI

## MCP (Model context Protocol)
    - Extracting the tools from an Application and move to a MCP server
    - Standardize how application provide context to LLMs like tools, prompts, etc.
    - MCP host, MCP server and MCP client
    - https://modelcontextprotocol.io/docs/2026-07-28/getting-started/intro

## MCP transport types
    - STDIO - standard Inputoutput
    - Streamable HTTP

## MCP Inspector
    - npx @modelcontextprotocol/inspector
    
## McpToolFilter