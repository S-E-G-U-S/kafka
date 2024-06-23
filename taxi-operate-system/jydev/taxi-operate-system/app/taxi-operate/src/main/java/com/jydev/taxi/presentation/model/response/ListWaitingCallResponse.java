package com.jydev.taxi.presentation.model.response;

import java.util.List;

public record ListWaitingCallResponse(
        List<ListWaitingCallItem> content
) {
    
    public record ListWaitingCallItem(
            long callId
    ) {
        
    }
}
