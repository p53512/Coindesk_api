package com.chen.coin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.chen.coin.service.CoindeskService;

import net.minidev.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:sql/data.sql") // sql 檔案放置的地方 /resources/sql/data.sql
public class TestUpdateCoin {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	CoindeskService coindeskService;
	
	//測試呼叫更新幣別對應表資料
		@Test
		public void testUpdateCoin() throws Exception {
		    JSONObject coinObject = new JSONObject();
		    coinObject.put("codename", "美元測試");
		    coinObject.put("rate_float",40927.7733);
//		    coinObject.put("symbol", "&#36;");
//		    coinObject.put("rate", "40,927.7733");
//		    coinObject.put("description", "United States Dollar");

		    // [Act] 模擬網路呼叫[PUT] /api/UpdateCoin/{id}
		    mockMvc.perform(MockMvcRequestBuilders.put("/api/updateCoindesk/USD")
		    		.accept(MediaType.APPLICATION_JSON) //response 設定型別
		            .contentType(MediaType.APPLICATION_JSON) // request 設定型別
		            .content(String.valueOf(coinObject))) // body 內容
		            .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()); // [Assert] 預期回應的status code 為 200(OK)
		}
}
