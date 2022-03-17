package com.chen.coin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.chen.coin.entity.Coindesk;
import com.chen.coin.service.CoindeskService;
import com.fasterxml.jackson.core.type.TypeReference;
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
	@Transactional
	public void testCreateCoin() throws Exception {
	    // [Arrange] 預期回傳的值
	    JSONObject coinObject = new JSONObject();
	    coinObject.put("code", "USD");
	    coinObject.put("codename", "美元");
	    coinObject.put("symbol", "&#36;");
	    coinObject.put("rate", "40,927.7733");
	    coinObject.put("rate_float", 40927.7733);
	    coinObject.put("description", "United States Dollar");

	    // [Act] 模擬網路呼叫[POST] /api/todos
//	    String returnString = mockMvc.perform(MockMvcRequestBuilders.post("/api/saveCoindesk")
//	            .accept(MediaType.APPLICATION_JSON) //response 設定型別
//	            .contentType(MediaType.APPLICATION_JSON) // request 設定型別
//	            .content(String.valueOf(coinObject))) // body 內容
//	            .andExpect(status().isCreated()) // 預期回應的status code 為 201(Created)
//	            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
	    
	    mockMvc.perform(MockMvcRequestBuilders.post("/api/saveCoindesk")
	            .accept(MediaType.APPLICATION_JSON) //response 設定型別
	            .contentType(MediaType.APPLICATION_JSON) // request 設定型別
	            .content(String.valueOf(coinObject))) // body 內容
	            .andExpect(status().isCreated()) // 預期回應的status code 為 201(Created)
	            .andDo(MockMvcResultHandlers.print());
	    
	    List<Coindesk> expectedList = new ArrayList<Coindesk>();
	    Coindesk expectedcoin = new Coindesk();
	    expectedcoin.setCode("USD");;
	    expectedcoin.setCodename("美元");
	    expectedcoin.setDescription("United States Dollar");
	    expectedcoin.setRate("40,927.7733");
	    expectedcoin.setRate_float(40927.7733);
	    expectedcoin.setSymbol("&#36;");
	    expectedList.add(expectedcoin);
	    
	}
	
//	@Test
//	public void testGetCoin() throws Exception {
//
//	    // [Arrange] 預期回傳的值
//	    List<Coindesk> expectedList = new ArrayList<Coindesk>();
//	    Coindesk coin = new Coindesk();
//	    coin.setCode("USD");;
//	    coin.setCodename("美元");
//	    coin.setDescription("&#36;");
//	    coin.setRate("40,927.7733");
//	    coin.setRate_float(40927.7733);
//	    coin.setSymbol("&#36;");
//	    expectedList.add(coin);
//
//	    // [Act] 模擬網路呼叫[GET] /api/todos
//	    String returnString = mockMvc.perform(MockMvcRequestBuilders.get("/api/getCoindesk")
//	            .accept(MediaType.APPLICATION_JSON ))
//	            .andExpect(status().isOk())
//	            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
//
//	    Iterable<Coindesk> actualList = objectMapper.readValue(returnString, new TypeReference<Iterable<Coindesk>>() {
//	    });
//	    // [Assert] 判定回傳的body是否跟預期的一樣
//	    assertEquals(expectedList,  actualList);
//	}
//	
//	@Test
//	public void testUpdateTodoSuccess() throws Exception {
//	    JSONObject todoObject = new JSONObject();
//	    todoObject.put("status", 2);
//
//	    // [Act] 模擬網路呼叫[PUT] /api/todos/{id}
//	    mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/1")
//	            .contentType(MediaType.APPLICATION_JSON) // request 設定型別
//	            .content(String.valueOf(todoObject))) // body 內容
//	            .andExpect(status().isOk()); // [Assert] 預期回應的status code 為 200(OK)
//	}
	
//	@Test
//	public void testDeleteTodoSuccess() throws Exception {
//	    // [Act] 模擬網路呼叫[DELETE] /api/todos/{id}
//	    mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/1")
//	            .contentType(MediaType.APPLICATION_JSON)) // request 設定型別
//	            .andExpect(status().isNoContent()); // [Assert] 預期回應的status code 為 204(No Content)
//	}
}
