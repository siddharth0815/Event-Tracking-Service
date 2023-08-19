package io.eventtracking.exceptions;

public class SchemaNotFoundException extends RuntimeException{
  public SchemaNotFoundException(String message){
    super(message);
  }
}
