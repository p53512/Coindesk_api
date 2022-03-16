package com.chen.coin;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.chen.coin.service.CoindeskService;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
//@Sql(scripts = "classpath:test/data.sql") // sql 檔案放置的地方
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // 在類中別的每個測試方法之前
public class TestCoindeskController {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	CoindeskService coindeskService;
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void testCreateCoin() throws Exception {
	    // [Arrange] 預期回傳的值
	    JSONObject todoObject = new JSONObject();
	    todoObject.put("code", "USD");
	    todoObject.put("codename", "美元");
	    todoObject.put("symbol", "&#36;");
	    todoObject.put("rate", "40,927.7733");
	    todoObject.put("rate_float", 40927.7733);
	    todoObject.put("description", "United States Dollar");

	    // [Act] 模擬網路呼叫[POST] /api/todos
	    String actualId = mockMvc.perform(MockMvcRequestBuilders.post("/api/saveCoindesk")
	            .accept(MediaType.APPLICATION_JSON) //response 設定型別
	            .contentType(MediaType.APPLICATION_JSON) // request 設定型別
	            .content(String.valueOf(todoObject))) // body 內容
	            .andExpect(status().isCreated()) // 預期回應的status code 為 201(Created)
	            .andReturn().getResponse().getContentAsString();

	    // [Assert] 判定回傳的body是否跟預期的一樣
	    assertEquals(2,  Integer.parseInt(actualId));
	}
	
}
