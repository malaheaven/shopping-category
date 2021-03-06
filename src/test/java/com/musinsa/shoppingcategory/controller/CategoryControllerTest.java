package com.musinsa.shoppingcategory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.shoppingcategory.dto.CategoryDto;
import com.musinsa.shoppingcategory.dto.RequestDto;
import com.musinsa.shoppingcategory.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@WebMvcTest(CategoryController.class)
@ExtendWith(RestDocumentationExtension.class)
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("???????????? ??????")
    void search() throws Exception {
        List<CategoryDto> result = Arrays.asList(CategoryDto.builder()
                .id(1L)
                .name("categoryName")
                .depth(1L)
                .children(Arrays.asList())
                .build());

        given(categoryService.search(any())).willReturn(result);

        mvc.perform(RestDocumentationRequestBuilders.get("/category").param("categoryId", "1")
                .contentType((MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value(200))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("$..result[0][?(@.id == '%s')]", "1").exists())
                .andExpect(jsonPath("$..result[0][?(@.name == '%s')]", "categoryName").exists())
                .andExpect(jsonPath("$..result[0][?(@.depth == '%s')]", "1").exists())
                .andExpect(jsonPath("$..result[0][?(@.children)]").exists())
                .andDo(print())
                .andDo(document("search-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        requestParameters(
                                parameterWithName("categoryId").description("???????????? ?????????").attributes(key("constraints").value("Default: ALL"))
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"))
                                .andWithPrefix("result.[0]", fieldWithPath("id").type(JsonFieldType.NUMBER).description("???????????? ?????????"))
                                .andWithPrefix("result.[0]", fieldWithPath("name").type(JsonFieldType.STRING).description("???????????? ??????"))
                                .andWithPrefix("result.[0]", fieldWithPath("depth").type(JsonFieldType.NUMBER).description("???????????? ??????"))
                                .andWithPrefix("result.[0]", fieldWithPath("children").type(JsonFieldType.ARRAY).description("?????? ????????????"))));

    }

    @Test
    @DisplayName("???????????? ??????")
    void add() throws Exception {

        RequestDto request = RequestDto.builder()
                .parentCategoryId(1L)
                .categoryName("categoryName")
                .build();

        CategoryDto result = CategoryDto.builder()
                .id(2L)
                .name("categoryName")
                .depth(2L)
                .children(Arrays.asList())
                .build();

        given(categoryService.addCategory(any())).willReturn(result);

        mvc.perform(RestDocumentationRequestBuilders.post("/category")
                .contentType((MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value(200))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("$..result[?(@.id == '%s')]", "2").exists())
                .andExpect(jsonPath("$..result[?(@.name == '%s')]", "categoryName").exists())
                .andExpect(jsonPath("$..result[?(@.depth == '%s')]", "2").exists())
                .andExpect(jsonPath("$..result[?(@.children)]").exists())
                .andDo(print())
                .andDo(document("add-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        requestFields(
                                fieldWithPath("parentCategoryId").type(JsonFieldType.NUMBER).description("????????? ?????? ???????????? ??????").attributes(key("constraints").value("?????? ????????? ????????? ????????? ??????????????? ??????")),
                                fieldWithPath("categoryName").type(JsonFieldType.STRING).description("????????? ???????????? ??????").optional()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"))
                                .andWithPrefix("result.", fieldWithPath("id").type(JsonFieldType.NUMBER).description("???????????? ?????????"))
                                .andWithPrefix("result.", fieldWithPath("name").type(JsonFieldType.STRING).description("???????????? ??????"))
                                .andWithPrefix("result.", fieldWithPath("depth").type(JsonFieldType.NUMBER).description("???????????? ??????"))
                                .andWithPrefix("result.", fieldWithPath("children").type(JsonFieldType.ARRAY).description("?????? ????????????"))));

    }

    @Test
    @DisplayName("???????????? ??????")
    void modify() throws Exception {

        RequestDto request = RequestDto.builder()
                .categoryName("changeName")
                .build();

        CategoryDto result = CategoryDto.builder()
                .id(1L)
                .name("changeName")
                .depth(1L)
                .children(Arrays.asList())
                .build();

        given(categoryService.modifyCategory(any(), any())).willReturn(result);

        mvc.perform(RestDocumentationRequestBuilders.put("/category/{categoryId}", "categoryId", 1)
                .contentType((MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value(200))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("$..result[?(@.id == '%s')]", "1").exists())
                .andExpect(jsonPath("$..result[?(@.name == '%s')]", "changeName").exists())
                .andExpect(jsonPath("$..result[?(@.depth == '%s')]", "1").exists())
                .andExpect(jsonPath("$..result[?(@.children)]").exists())
                .andDo(print())
                .andDo(document("update-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("???????????? ?????????")
                        ),
                        requestFields(
                                fieldWithPath("categoryName").type(JsonFieldType.STRING).description("????????? ???????????? ??????").optional()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"))
                                .andWithPrefix("result.", fieldWithPath("id").type(JsonFieldType.NUMBER).description("???????????? ?????????"))
                                .andWithPrefix("result.", fieldWithPath("name").type(JsonFieldType.STRING).description("???????????? ??????"))
                                .andWithPrefix("result.", fieldWithPath("depth").type(JsonFieldType.NUMBER).description("???????????? ??????"))
                                .andWithPrefix("result.", fieldWithPath("children").type(JsonFieldType.ARRAY).description("?????? ????????????"))));
    }


    @Test
    @DisplayName("???????????? ??????")
    void delete() throws Exception {
        mvc.perform(RestDocumentationRequestBuilders.delete("/category/{categoryId}", "categoryId", 1)
                .contentType((MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value(200))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(print())
                .andDo(document("delete-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("???????????? ?????????")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"))));
    }
}
