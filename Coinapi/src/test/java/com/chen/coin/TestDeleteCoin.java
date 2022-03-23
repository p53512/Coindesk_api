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
@Sql(scripts = "classpath:sql/data.sql") // sql 檔案放置的地方 /resources/sql/data.sql
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // 在類中別的每個測試方法之前
public class TestDeleteCoin {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	CoindeskService coindeskService;
	
	//測試呼叫刪除幣別對應表資料
		@Test
		public void testDeleteCoin() throws Exception {
		    // [Act] 模擬網路呼叫[DELETE] /api/DeleteCoin/{id}
		    mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteCoin/USD")
		            .contentType(MediaType.APPLICATION_JSON)) // request 設定型別
		            .andExpect(status().isNoContent()) // [Assert] 預期回應的status code 為 204(No Content)
		            .andDo(MockMvcResultHandlers.print());
		}
}
