package com.chen.coin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.chen.coin.service.CoindeskService;

import net.minidev.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // 在類中別的每個測試方法之前
public class TestCreateCoin {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	CoindeskService coindeskService;
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
		            .andExpect(status().isCreated()) // 預期回應的status code 為 201(Created)
		            .andDo(MockMvcResultHandlers.print());
		}

}
