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
