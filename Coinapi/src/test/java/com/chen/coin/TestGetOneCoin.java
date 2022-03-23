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

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:sql/data.sql") // sql 檔案放置的地方 /resources/sql/data.sql
public class TestGetOneCoin {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	CoindeskService coindeskService;
	
	//測試呼叫查詢幣別對應表資料
		@Test
		public void testGetCoin() throws Exception {
			
			//[Act] 模擬網路呼叫[GET] /api/getCoindesk
			mockMvc.perform(MockMvcRequestBuilders.get("/api/getCoindesk/USD")
					.accept(MediaType.APPLICATION_JSON ))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
		}
	
}
