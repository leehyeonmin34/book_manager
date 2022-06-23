package com.leehyeonmin.book_project.domain.controller;


import com.google.gson.Gson;
import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublisherRestControllerTest {

    @InjectMocks
    PublisherRestController publisherRestController;

    @Mock
    PublisherService publisherService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() { mockMvc = MockMvcBuilders
            .standaloneSetup(publisherRestController)
            .build();
    }

    @Test
    @DisplayName("get all publishers")
    public void getAllPublishers() throws Exception{
        //given
        PublisherDto.GetListResponse mockResponse = publisherListResponse();
        lenient().when(publisherService.getAllPublishers()).thenReturn(mockResponse);

        // when
        ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/api/publishers"));


        // then
        MvcResult result = ra.andExpect(status().isOk()).andReturn();
        PublisherDto.GetListResponse response = new Gson().fromJson(result.getResponse().getContentAsString(), PublisherDto.GetListResponse.class);
        assertThat(response.getTotal()).isEqualTo(mockResponse.getTotal());
    }


    private PublisherDto.GetListResponse publisherListResponse(){
        List<PublisherDto.GetResponse> list = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++){
            list.add(PublisherDto.GetResponse.builder()
                    .name("이름")
                    .id(Long.valueOf(i))
                    .build()
            );
        }
        return PublisherDto.GetListResponse.builder()
                .publishers(list)
                .total(list.size())
                .build();
    }

}
