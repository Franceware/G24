package com.example.demo.entity;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Mail 
{
    private String from;
    private String to;
    private String subject;
    private Map<String, Object> model;
}
