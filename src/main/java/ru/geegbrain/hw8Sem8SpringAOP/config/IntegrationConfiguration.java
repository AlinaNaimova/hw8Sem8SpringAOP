package ru.geegbrain.hw8Sem8SpringAOP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfiguration {

    //getting first channel
    @Bean
    public MessageChannel messageChannelInput() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel messageChannelFileWriter() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "messageChannelInput",
            outputChannel = "messageChannelFileWriter")
    public GenericTransformer<String, String> myTransformer() {
        return text -> {
            return text.toUpperCase();
        };
    }


    @Bean
    @ServiceActivator(inputChannel = "messageChannelFileWriter")
    public FileWritingMessageHandler myFileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/Users/alina/IdeaProjects/Spring/seminar8/hw5sem5/src/main/java/ru/geegbrain/hw8Sem8SpringAOP"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }


}

