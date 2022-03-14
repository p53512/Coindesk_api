package com.chen.coin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.chen.coin.entity.Coindesk;
import com.chen.coin.service.CoindeskService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Coindesktest.class)
@WebAppConfiguration
public class Coindesktest {

	@Autowired
	private CoindeskService coindeskService;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private WebApplicationContext webApplicationContext;

	 MockMvc mvc;

	Coindesk coindesk;
	
	@Before
	public void setup() {
		coindesk = new Coindesk();
		coindesk.setCode("USD");
		coindesk.setCodename("美元");
		coindesk.setDescription("United States Dollar");
		coindesk.setRate("40,927.7733");
		coindesk.setRate_float(40927.7733);
		coindesk.setSymbol("&#36;");
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void contextLoads() throws Exception {
		String uri = "/coindesk/1";
		try{
			MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).content(objectMapper.writeValueAsString(coindesk)).accept(MediaType.APPLICATION_JSON)).andReturn();
			int status = result.getResponse().getStatus();
			Assert.assertEquals("錯誤",200,status);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}