package com.dnd.eight.Controller.Message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StartResponseMessage {
    private List<String> questions;
    private Integer roundNumber;
}
