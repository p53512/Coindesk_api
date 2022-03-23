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
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // 在類中別的每個測試方法之前
public class TestCallCoindesApi {

	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	CoindeskService coindeskService;
	
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
}
