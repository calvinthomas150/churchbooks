package org.churchbooks.churchbooks.transactions.dtos;

import lombok.Getter;

import java.net.URI;
@Getter
public class TransactionsFromOfxRequest {
    URI ofxFilePath;
}
