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
    @DisplayName("카테고리 조회")
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
                                parameterWithName("categoryId").description("카테고리 아이디").attributes(key("constraints").value("Default: ALL"))
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"))
                                .andWithPrefix("result.[0]", fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 아이디"))
                                .andWithPrefix("result.[0]", fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름"))
                                .andWithPrefix("result.[0]", fieldWithPath("depth").type(JsonFieldType.NUMBER).description("카테고리 깊이"))
                                .andWithPrefix("result.[0]", fieldWithPath("children").type(JsonFieldType.ARRAY).description("하위 카테고리"))));

    }

    @Test
    @DisplayName("카테고리 등록")
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
                                fieldWithPath("parentCategoryId").type(JsonFieldType.NUMBER).description("등록할 부모 카테고리 이름").attributes(key("constraints").value("해당 데이터 없으면 최상위 카테고리로 등록")),
                                fieldWithPath("categoryName").type(JsonFieldType.STRING).description("등록할 카테고리 이름").optional()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"))
                                .andWithPrefix("result.", fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 아이디"))
                                .andWithPrefix("result.", fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름"))
                                .andWithPrefix("result.", fieldWithPath("depth").type(JsonFieldType.NUMBER).description("카테고리 깊이"))
                                .andWithPrefix("result.", fieldWithPath("children").type(JsonFieldType.ARRAY).description("하위 카테고리"))));

    }

    @Test
    @DisplayName("카테고리 수정")
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
                                parameterWithName("categoryId").description("카테고리 아이디")
                        ),
                        requestFields(
                                fieldWithPath("categoryName").type(JsonFieldType.STRING).description("변경할 카테고리 이름").optional()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"))
                                .andWithPrefix("result.", fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 아이디"))
                                .andWithPrefix("result.", fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름"))
                                .andWithPrefix("result.", fieldWithPath("depth").type(JsonFieldType.NUMBER).description("카테고리 깊이"))
                                .andWithPrefix("result.", fieldWithPath("children").type(JsonFieldType.ARRAY).description("하위 카테고리"))));
    }


    @Test
    @DisplayName("카테고리 삭제")
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
                                parameterWithName("categoryId").description("카테고리 아이디")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"))));
    }
}
