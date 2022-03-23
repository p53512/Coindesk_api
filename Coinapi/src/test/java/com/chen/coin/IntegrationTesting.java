package com.chen.coin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.chen.coin.service.CoindeskService;

import net.minidev.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:sql/data.sql") // sql 檔案放置的地方 /resources/sql/data.sql
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // 在類中別的每個測試方法之前
public class IntegrationTesting {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	CoindeskService coindeskService;
	
	//測試呼叫查詢幣別對應表資料
	@Test
	public void testGetCoin() throws Exception {
		
		//[Act] 模擬網路呼叫[GET] /api/getCoindesk
		mockMvc.perform(MockMvcRequestBuilders.get("/api/getCoindesk")
				.accept(MediaType.APPLICATION_JSON ))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	
	//測試呼叫新增幣別對應表資料
	@Test
	public void testCreateCoin() throws Exception {
	    JSONObject coinObject = new JSONObject();
	    coinObject.put("code", "USD");
	    coinObject.put("codename", "美元");
	    coinObject.put("symbol", "&#36;");
	    coinObject.put("rate", "30,927.7733");
	    coinObject.put("rate_float", 30927.7733);
	    coinObject.put("description", "United States Dollar");

	    mockMvc.perform(MockMvcRequestBuilders.post("/api/saveCoindesk")
	            .accept(MediaType.APPLICATION_JSON) //response 設定型別
	            .contentType(MediaType.APPLICATION_JSON) // request 設定型別
	            .content(String.valueOf(coinObject))) // body 內容
	            .andExpect(status().isCreated()); // 預期回應的status code 為 201(Created)
//	            .andDo(MockMvcResultHandlers.print());
	}
	
	//測試呼叫更新幣別對應表資料
	@Test
	public void testUpdateCoin() throws Exception {
	    JSONObject coinObject = new JSONObject();
	    coinObject.put("codename", "美元測試");
	    coinObject.put("rate_float",40927.7733);
//	    coinObject.put("symbol", "&#36;");
//	    coinObject.put("rate", "40,927.7733");
//	    coinObject.put("description", "United States Dollar");

	    // [Act] 模擬網路呼叫[PUT] /api/UpdateCoin/{id}
	    mockMvc.perform(MockMvcRequestBuilders.put("/api/updateCoindesk/USD")
	    		.accept(MediaType.APPLICATION_JSON) //response 設定型別
	            .contentType(MediaType.APPLICATION_JSON) // request 設定型別
	            .content(String.valueOf(coinObject))) // body 內容
	            .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()); // [Assert] 預期回應的status code 為 200(OK)
	}
	
	//測試呼叫刪除幣別對應表資料
	@Test
	public void testDeleteCoin() throws Exception {
	    // [Act] 模擬網路呼叫[DELETE] /api/DeleteCoin/{id}
	    mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteCoin/USD")
	            .contentType(MediaType.APPLICATION_JSON)) // request 設定型別
	            .andExpect(status().isNoContent()); // [Assert] 預期回應的status code 為 204(No Content)
	}
	
	//測試呼叫coindesk API
	@Test
	public void callCoindesk() throws Exception {

	     //[Act] 模擬網路呼叫[GET] /api/callCoindesk
	     mockMvc.perform(
	    		 MockMvcRequestBuilders.get("/api/callCoindesk/https%3A%2F%2Fapi.coindesk.com%2Fv1%2Fbpi%2Fcurrentprice.json")
	            .accept(MediaType.APPLICATION_JSON ))
	            .andExpect(status().isOk())
	            .andDo(MockMvcResultHandlers.print());

	}
	
	//進行資料轉換，組成新API
	@Test
	public void turnCoinApi() throws Exception {
		
		//[Act] 模擬網路呼叫[GET] /api/turnCoinAPi
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/turnCoinApi/https%3A%2F%2Fapi.coindesk.com%2Fv1%2Fbpi%2Fcurrentprice.json")
				.accept(MediaType.APPLICATION_JSON ))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
	}
	
}
