package com.org.CoronaReport;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CoronaReportService {
	
	@Autowired
	PagingRepository pagingRepository;
	
	private static String VIRUS_DATA_URL_OURWORLDDATA = "https://covid.ourworldindata.org/data/owid-covid-data.csv";
	
	List<CovidData> covidList = new ArrayList<CovidData>();

	HttpClient client = HttpClient.newHttpClient();
	HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL_OURWORLDDATA)).build();
	
	public List<CovidData> getCovidList(){
		return covidList;
	}
	
	public void storeToH2Database() {
		if(pagingRepository.count() > 0) {
			pagingRepository.deleteAll();
		}
		pagingRepository.saveAll(covidList);
	}
	
	public void fetchCovidData() throws IOException, InterruptedException, ParseException{
		List<CovidData> covidList = new ArrayList<CovidData>();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		for(CSVRecord record: records) {
			CovidData covidData = new CovidData();
			covidData.fillData(record);
			covidList.add(covidData);
		}
		this.covidList = covidList;
	}	

}
