package scraping;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.text.AbstractDocument.LeafElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import datatypes.StrArrayKeyValueVO;
import datatypes.StrColArrayVO;
import datatypes.StrDoubleArrayKeyValueVO;

import static scraping.Constants.*;


public class Game {

	

	public Game() {
		// TODO Auto-generated constructor stub
	}

	public Element extractGame(Document documento) {
		ArrayList<Element> teams = this.extractData(documento);
		Element teamAway = teams.get(0);
		Element teamHome = teams.get(1);

//		for (Element elementTeamAway : this.extractSummary(documento, "a")) {
//			teamAway.appendChild(elementTeamAway);
//		}
//		for (Element elementTeamHome : this.extractSummary(documento, "b")) {
//			teamHome.appendChild(elementTeamHome);
//		}

		Element game = documento.createElement(GAME).appendChild(teamAway);
		game.appendChild(teamHome);
		
		//game date
		StrArrayKeyValueVO gameDate = this.extractDateGame(documento);
		game.attr(gameDate.getStrKey(), gameDate.getStrOriginal());
		Enumeration<String> keysValuesDate = gameDate.getValues().keys(); 
		while (keysValuesDate.hasMoreElements()) {
			String keyValue = keysValuesDate.nextElement();
			String value = gameDate.getValues().get(keyValue);
			game.attr(keyValue, value);
		}
		
		//game start time
		StrArrayKeyValueVO gameStartTime = this.extractStartTimeGame(documento);
		game.attr(gameStartTime.getStrKey(), gameStartTime.getStrOriginal());
		Enumeration<String> keysValues = gameStartTime.getValues().keys(); 
		while (keysValues.hasMoreElements()) {
			String keyValue = keysValues.nextElement();
			String value = gameStartTime.getValues().get(keyValue);
			game.attr(keyValue, value);
		}
		
		//game venue game
		StrArrayKeyValueVO gameVenue = this.extractVenueGame(documento);
		game.attr(gameVenue.getStrKey(), gameVenue.getStrOriginal());
		Enumeration<String> keysValuesVenue = gameVenue.getValues().keys(); 
		while (keysValuesVenue.hasMoreElements()) {
			String keyValue = keysValuesVenue.nextElement();
			String value = gameVenue.getValues().get(keyValue);
			game.attr(keyValue, value);
		}
		
		//game linescore_wrap Home team
		StrDoubleArrayKeyValueVO gameLineScoreHome = this.extractLineScoreHome(documento);
		teamHome.attr(gameLineScoreHome.getStrKey(), gameLineScoreHome.getStrOriginal());
		Enumeration<String> keysValuesLineHome = gameLineScoreHome.getValues().keys(); 
		Element lineScoreHome = documento.createElement(TEAM_LINESCORE);
		while (keysValuesLineHome.hasMoreElements()) {
			String keyValue = keysValuesLineHome.nextElement();
			String value = gameLineScoreHome.getValues().get(keyValue);
			Element lineScoreInn = documento.createElement(TEAM_LINESCORE_INN);
			lineScoreInn.attr(TEAM_LINESCORE_INN_INN, keyValue);
			lineScoreInn.attr(TEAM_LINESCORE_INN_RUN, value);
			lineScoreHome.appendChild(lineScoreInn);
		}
		teamHome.appendChild(lineScoreHome);
		
		Enumeration<String> keysValuesLineHomeRHE = gameLineScoreHome.getValues2().keys(); 
		
		while (keysValuesLineHomeRHE.hasMoreElements()) {
			
			String keyValue = keysValuesLineHomeRHE.nextElement();
			String value = gameLineScoreHome.getValues2().get(keyValue);
			
			teamHome.attr(keyValue, value);
		}
		
		//game linescore_wrap Away team
		StrDoubleArrayKeyValueVO gameLineScoreAway = this.extractLineScoreAway(documento);
		teamAway.attr(gameLineScoreAway.getStrKey(), gameLineScoreAway.getStrOriginal());
		Enumeration<String> keysValuesLineAway = gameLineScoreAway.getValues().keys(); 
		Element lineScoreAway = documento.createElement(TEAM_LINESCORE);
		while (keysValuesLineAway.hasMoreElements()) {
			String keyValue = keysValuesLineAway.nextElement();
			String value = gameLineScoreAway.getValues().get(keyValue);
			Element lineScoreInn = documento.createElement(TEAM_LINESCORE_INN);
			lineScoreInn.attr(TEAM_LINESCORE_INN_INN, keyValue);
			lineScoreInn.attr(TEAM_LINESCORE_INN_RUN, value);
			lineScoreAway.appendChild(lineScoreInn);
		}
		teamAway.appendChild(lineScoreAway);
		
		Enumeration<String> keysValuesLineAwayRHE = gameLineScoreAway.getValues2().keys(); 
		
		while (keysValuesLineAwayRHE.hasMoreElements()) {
			
			String keyValue = keysValuesLineAwayRHE.nextElement();
			String value = gameLineScoreAway.getValues2().get(keyValue);
			
			teamAway.attr(keyValue, value);
		}
		
		//game linescore winner-lost-save
		this.extractLinePitcherWinnerLostSave(documento);
		
		
//		game.attr("asistencia", this.extractAsistencia(documento));
//		game.attr("datetime", this.extractFecha(documento));
//		game.attr("datetimeepoch", this.extractFechaEpoch(documento));
//
//		this.extractSummary(documento, "a");
//		this.extractSummary(documento, "b");
//		Element game = documento.createElement("game");
		
//		game.attr("prueba","okLllego");

		return game;
	}
	
	private void extractLinePitcherWinnerLostSave(Document documento) {
		Elements lineScoreElements = documento.select("div > div > div.linescore_wrap > table > tfoot > tr > td");
		System.out.println(lineScoreElements.get(0).text());
		String strWinnerLosserSave = lineScoreElements.get(0).text();
		String [] colStr = strWinnerLosserSave.split("�");
		System.out.println(colStr.length);
		StrColArrayVO strColArrayVOWinner = new StrColArrayVO(); 
		strColArrayVOWinner.setStrKey("");
		
		if (colStr.length >= 2) {
			
			String strWinner = colStr[0];
			
			
			String strLosser = colStr[1];
		}
		
	}

	private StrDoubleArrayKeyValueVO extractLineScoreHome(Document documento) {
		StrDoubleArrayKeyValueVO valueReturn = new StrDoubleArrayKeyValueVO();
		Elements lineScoreElements = documento.select("div > div > div.linescore_wrap > table > tbody > tr");
		valueReturn.setStrKey(TEAM_LINESCORE_ORIGINAL);
		valueReturn.setStrOriginal(lineScoreElements.get(1).html());
		Elements colLineScoreTD = lineScoreElements.get(1).select("td");
		for (int i = 2; i < colLineScoreTD.size()-3; i++) {
			valueReturn.getValues().put(""+(i-1), colLineScoreTD.get(i).text());
		}
		valueReturn.getValues2().put(TEAM_LINESCORE_ERRORS, colLineScoreTD.get(colLineScoreTD.size()-1).text());
		valueReturn.getValues2().put(TEAM_LINESCORE_HITS, colLineScoreTD.get(colLineScoreTD.size()-2).text());
		valueReturn.getValues2().put(TEAM_LINESCORE_RUNS, colLineScoreTD.get(colLineScoreTD.size()-3).text());
		return valueReturn;
	}
	private StrDoubleArrayKeyValueVO extractLineScoreAway(Document documento) {
		StrDoubleArrayKeyValueVO valueReturn = new StrDoubleArrayKeyValueVO();
		Elements lineScoreElements = documento.select("div > div > div.linescore_wrap > table > tbody > tr");
		valueReturn.setStrKey(TEAM_LINESCORE_ORIGINAL);
		valueReturn.setStrOriginal(lineScoreElements.get(0).html());
		Elements colLineScoreTD = lineScoreElements.get(0).select("td");
		for (int i = 2; i < colLineScoreTD.size()-3; i++) {
			valueReturn.getValues().put(""+(i-1), colLineScoreTD.get(i).text());
		}
		valueReturn.getValues2().put(TEAM_LINESCORE_ERRORS, colLineScoreTD.get(colLineScoreTD.size()-1).text());
		valueReturn.getValues2().put(TEAM_LINESCORE_HITS, colLineScoreTD.get(colLineScoreTD.size()-2).text());
		valueReturn.getValues2().put(TEAM_LINESCORE_RUNS, colLineScoreTD.get(colLineScoreTD.size()-3).text());
		return valueReturn;
	}

	private StrArrayKeyValueVO extractDateGame(Document documento) {
		StrArrayKeyValueVO valueReturn = new StrArrayKeyValueVO();
		Elements scoreboxElements = documento.select("div > div > div.scorebox > div.scorebox_meta > div");
		String strOriginal = scoreboxElements.get(0).text();
		valueReturn.setStrKey(GAME_DATE_ORIGINAL);
		valueReturn.setStrOriginal(strOriginal);
		String [] colDate = strOriginal.split(",");
		String monthDay = colDate[1];
		String year = colDate[2].trim();
		String [] colMonthDay = monthDay.trim().split(" ");
		String month = colMonthDay[0].trim();
		String day = colMonthDay[1].trim();
		valueReturn.getValues().put(GAME_DATE_DAY, day);
		valueReturn.getValues().put(GAME_DATE_MONTH, _findMonthNumber(month));
		valueReturn.getValues().put(GAME_DATE_YEAR, year);
 		return valueReturn;
		
	}
	private String _findMonthNumber(String strMonth) {
		String strReturn = "1";
		switch (strMonth) {
		case "January":
			strReturn = "1";
			break;
		case "February":
			strReturn = "2";
			break;
		case "March":
			strReturn = "3";
			break;
		case "April":
			strReturn = "4";
			break;
		case "May":
			strReturn = "5";
			break;
		case "June":
			strReturn = "6";
			break;
		case "July":
			strReturn = "7";
			break;
		case "August":
			strReturn = "8";
			break;
		case "September":
			strReturn = "9";
			break;
		case "October":
			strReturn = "10";
			break;
		case "November":
			strReturn = "11";
			break;
		case "December":
			strReturn = "12";
			break;
		}
		return strReturn;
	}
	
	private StrArrayKeyValueVO extractStartTimeGame(Document documento) {
		StrArrayKeyValueVO valueReturn = new StrArrayKeyValueVO();
		Elements scoreboxElements = documento.select("div > div > div.scorebox > div.scorebox_meta > div");
		String str = "";
		str = scoreboxElements.get(1).text();
		valueReturn.setStrOriginal(str);
		valueReturn.setStrKey(GAME_START_TIME_ORIGINAL);
		String strStartTime = str.replaceAll("Start Time:", "").replaceAll("Local", "").trim();
		String[] colStartTime = strStartTime.split(" ");
		int hora = 0;
		if (colStartTime[1].contains("p")) {hora = 12;}
		String [] colHoraMinuto = colStartTime[0].split(":");
		String strHora = colHoraMinuto[0];
		String strMinuto = colHoraMinuto[1];
		int intHora = Integer.parseInt(strHora) + hora;
		valueReturn.getValues().put(GAME_DATE_HOUR, intHora + "");
		valueReturn.getValues().put(GAME_DATE_MINUTE, strMinuto);
		return valueReturn;
		
	}
	private StrArrayKeyValueVO extractVenueGame(Document documento) {
		StrArrayKeyValueVO valueReturn = new StrArrayKeyValueVO();
		Element VenueGameElement = documento.select("div  > strong:contains(Venue)").first().parents().first();
		String strVenueGameText = VenueGameElement.html();
		String[] strTimeOfGameArray = strVenueGameText.split("</strong>");
		String strVenueGame = strTimeOfGameArray[1].trim();
		valueReturn.setStrOriginal(strVenueGameText);
		valueReturn.setStrKey(GAME_VENUE_ORIGINAL);
		valueReturn.getValues().put(GAME_VENUE, strVenueGame.replaceAll("\"", "").replaceAll(":", "").trim());
		return valueReturn;
		
	}

	private Element extractSummaryGoal(Document documento, String typeTeam) {
		Element matchSummary = documento.select("div#events_wrap").first();
		Elements teamSummaryGoal = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.goal");
		Element goals = documento.createElement("goals");
		for (Element elementGoal : teamSummaryGoal) {
			Element goal = documento.createElement("goal");
			goal.attr("anota_player_name", elementGoal.parent().select("div>div>a").first().text());
			goal.attr("anota_player_id", this.idPlayer(elementGoal.parent().select("div>div>a").first().attr("href")));
			goal.attr("asiste_player_name", elementGoal.parent().select("div>small>a").first().text());
			goal.attr("asiste_player_id",
					this.idPlayer(elementGoal.parent().select("div>small>a").first().attr("href")));
			String minutoAgregado = elementGoal.parent().parent().select("div").get(1).ownText()
					.replaceAll("\u00a0", "").replaceAll("�", "");
			if (minutoAgregado.contains("+")) {
				String[] arrMinutoAgregado = minutoAgregado.split("\\+");
				goal.attr("minute", arrMinutoAgregado[0].trim());
				goal.attr("agregado", arrMinutoAgregado[1].trim());
			} else {
				goal.attr("minute", minutoAgregado.trim());
			}
			String scoreCompleto = elementGoal.parent().parent().select("div>small>span").first().ownText().trim();
			String[] arrScoreCompleto = scoreCompleto.split(":");
			goal.attr("goalFavor", arrScoreCompleto[0]);
			goal.attr("goalContra", arrScoreCompleto[1]);
			goals.appendChild(goal);
		}
		return goals;
	}

	private Element extractSummarySustitute(Document documento, String typeTeam) {
		Element matchSummary = documento.select("div#events_wrap").first();
		Elements teamSummaryEvent = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.substitute_in");
		Element events = documento.createElement("substitutes");
		for (Element elementEvent : teamSummaryEvent) {
			Element event = documento.createElement("substitute");
			event.attr("in_player_name", elementEvent.parent().select("div>div>a").first().text());
			event.attr("in_player_id", this.idPlayer(elementEvent.parent().select("div>div>a").first().attr("href")));
			event.attr("out_player_name", elementEvent.parent().select("div>small>a").first().text());
			event.attr("out_player_id",
					this.idPlayer(elementEvent.parent().select("div>small>a").first().attr("href")));
			String minutoAgregado = elementEvent.parent().parent().select("div").get(1).ownText()
					.replaceAll("\u00a0", "").replaceAll("�", "");
			if (minutoAgregado.contains("+")) {
				String[] arrMinutoAgregado = minutoAgregado.split("\\+");
				event.attr("minute", arrMinutoAgregado[0].trim());
				event.attr("agregado", arrMinutoAgregado[1].trim());
			} else {
				event.attr("minute", minutoAgregado.trim());
			}
			String scoreCompleto = elementEvent.parent().parent().select("div>small>span").first().ownText().trim();
			String[] arrScoreCompleto = scoreCompleto.split(":");
			event.attr("goalFavor", arrScoreCompleto[0]);
			event.attr("goalContra", arrScoreCompleto[1]);
			events.appendChild(event);
		}
		return events;
	}

	private Element extractSummaryYellowCard(Document documento, String typeTeam) {
		Element matchSummary = documento.select("div#events_wrap").first();
		Elements teamSummaryEvent = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.yellow_card");
		Element goals = documento.createElement("yellow_cards");
		for (Element elementEvent : teamSummaryEvent) {
			Element goal = documento.createElement("yellow_card");
			goal.attr("player_name", elementEvent.parent().select("div>div>a").first().text());
			goal.attr("player_id", this.idPlayer(elementEvent.parent().select("div>div>a").first().attr("href")));
			String minutoAgregado = elementEvent.parent().parent().select("div").get(1).ownText()
					.replaceAll("\u00a0", "").replaceAll("�", "");
			if (minutoAgregado.contains("+")) {
				String[] arrMinutoAgregado = minutoAgregado.split("\\+");
				goal.attr("minute", arrMinutoAgregado[0].trim());
				goal.attr("agregado", arrMinutoAgregado[1].trim());
			} else {
				goal.attr("minute", minutoAgregado.trim());
			}
			String scoreCompleto = elementEvent.parent().parent().select("div>small>span").first().ownText().trim();
			String[] arrScoreCompleto = scoreCompleto.split(":");
			goal.attr("goalFavor", arrScoreCompleto[0]);
			goal.attr("goalContra", arrScoreCompleto[1]);
			goals.appendChild(goal);
		}
		return goals;
	}

	private Element extractSummaryYellowRedCard(Document documento, String typeTeam) {
		Element matchSummary = documento.select("div#events_wrap").first();
		Elements teamSummaryEvent = matchSummary
				.select("div.event." + typeTeam + ">div>div.event_icon.yellow_red_card");
		Element goals = documento.createElement("yellow_red_cards");
		for (Element elementEvent : teamSummaryEvent) {
			Element goal = documento.createElement("yellow_red_card");
			goal.attr("player_name", elementEvent.parent().select("div>div>a").first().text());
			goal.attr("player_id", this.idPlayer(elementEvent.parent().select("div>div>a").first().attr("href")));
			String minutoAgregado = elementEvent.parent().parent().select("div").get(1).ownText()
					.replaceAll("\u00a0", "").replaceAll("�", "");
			if (minutoAgregado.contains("+")) {
				String[] arrMinutoAgregado = minutoAgregado.split("\\+");
				goal.attr("minute", arrMinutoAgregado[0].trim());
				goal.attr("agregado", arrMinutoAgregado[1].trim());
			} else {
				goal.attr("minute", minutoAgregado.trim());
			}
			String scoreCompleto = elementEvent.parent().parent().select("div>small>span").first().ownText().trim();
			String[] arrScoreCompleto = scoreCompleto.split(":");
			goal.attr("goalFavor", arrScoreCompleto[0]);
			goal.attr("goalContra", arrScoreCompleto[1]);
			goals.appendChild(goal);
		}
		return goals;
	}

	private Element extractSummaryRedCard(Document documento, String typeTeam) {
		Element matchSummary = documento.select("div#events_wrap").first();
		Elements teamSummaryEvent = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.red_card");
		Element goals = documento.createElement("red_cards");
		for (Element elementEvent : teamSummaryEvent) {
			Element goal = documento.createElement("red_card");
			goal.attr("player_name", elementEvent.parent().select("div>div>a").first().text());
			goal.attr("player_id", this.idPlayer(elementEvent.parent().select("div>div>a").first().attr("href")));
			String minutoAgregado = elementEvent.parent().parent().select("div").get(1).ownText()
					.replaceAll("\u00a0", "").replaceAll("�", "");
			if (minutoAgregado.contains("+")) {
				String[] arrMinutoAgregado = minutoAgregado.split("\\+");
				goal.attr("minute", arrMinutoAgregado[0].trim());
				goal.attr("agregado", arrMinutoAgregado[1].trim());
			} else {
				goal.attr("minute", minutoAgregado.trim());
			}
			String scoreCompleto = elementEvent.parent().parent().select("div>small>span").first().ownText().trim();
			String[] arrScoreCompleto = scoreCompleto.split(":");
			goal.attr("goalFavor", arrScoreCompleto[0]);
			goal.attr("goalContra", arrScoreCompleto[1]);
			goals.appendChild(goal);
		}
		return goals;
	}

	private Element extractSummaryPenalty(Document documento, String typeTeam) {
		Element matchSummary = documento.select("div#events_wrap").first();
		Elements teamSummaryEvent = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.penalty_goal");

		Element events = documento.createElement("penaltys");
			for (Element elementEvent : teamSummaryEvent) {
				Element event = documento.createElement("penalty");
				event.attr("player_name", elementEvent.parent().select("div>div>a").first().text());
				event.attr("player_id", this.idPlayer(elementEvent.parent().select("div>div>a").first().attr("href")));
				String minutoAgregado = elementEvent.parent().parent().select("div").get(1).ownText()
						.replaceAll("\u00a0", "").replaceAll("�", "");
				if (minutoAgregado.contains("+")) {
					String[] arrMinutoAgregado = minutoAgregado.split("\\+");
					event.attr("minute", arrMinutoAgregado[0].trim());
					event.attr("agregado", arrMinutoAgregado[1].trim());
				} else {
					event.attr("minute", minutoAgregado.trim());
				}
				String scoreCompleto = elementEvent.parent().parent().select("div>small>span").first().ownText().trim();
				String[] arrScoreCompleto = scoreCompleto.split(":");
				event.attr("goalFavor", arrScoreCompleto[0]);
				event.attr("goalContra", arrScoreCompleto[1]);
				events.appendChild(event);
			}
		return events;

	}
	
	private Element extractSummaryOwnGoal(Document documento, String typeTeam) {
		Element matchSummary = documento.select("div#events_wrap").first();
		Elements teamSummaryEvent = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.own_goal");
		Element events = documento.createElement("own_goals");
		for (Element elementEvent : teamSummaryEvent) {
			Element event = documento.createElement("own_goal");
			event.attr("player_name", elementEvent.parent().select("div>div>a").first().text());
			event.attr("player_id", this.idPlayer(elementEvent.parent().select("div>div>a").first().attr("href")));
			String minutoAgregado = elementEvent.parent().parent().select("div").get(1).ownText()
					.replaceAll("\u00a0", "").replaceAll("�", "");
			if (minutoAgregado.contains("+")) {
				String[] arrMinutoAgregado = minutoAgregado.split("\\+");
				event.attr("minute", arrMinutoAgregado[0].trim());
				event.attr("agregado", arrMinutoAgregado[1].trim());
			} else {
				event.attr("minute", minutoAgregado.trim());
			}
			String scoreCompleto = elementEvent.parent().parent().select("div>small>span").first().ownText().trim();
			String[] arrScoreCompleto = scoreCompleto.split(":");
			event.attr("goalFavor", arrScoreCompleto[0]);
			event.attr("goalContra", arrScoreCompleto[1]);
			events.appendChild(event);
		}
		return events;
	}

	private ArrayList<Element> extractSummary(Document documento, String typeTeam) {
		ArrayList<Element> arrSummary = new ArrayList<Element>();
		Element matchSummary = documento.select("div#events_wrap").first();

		arrSummary.add(extractSummaryGoal(documento, typeTeam));

//		Elements teamSummaryOwnGoal = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.own_goal");
//		Element own_goal = documento.createElement("own_goal");
		arrSummary.add(extractSummaryOwnGoal(documento, typeTeam));
//		System.out.println("ownGoal "+teamSummaryOwnGoal.size());

//		Elements teamSummaryYellowCard = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.yellow_card");
//		Element yellow_card = documento.createElement("yellow_card");
		arrSummary.add(extractSummaryYellowCard(documento, typeTeam));
//		System.out.println("yellow "+teamSummaryYellowCard.size());

//		Elements teamSummarySubtituteIn = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.substitute_in");
//		Element substitute_in = documento.createElement("substitute_in");
		arrSummary.add(extractSummarySustitute(documento, typeTeam));
//		System.out.println("subtitute "+teamSummarySubtituteIn.size());

//		Elements teamSummaryPenaltyGoal = matchSummary.select("div.event." + typeTeam + ">div>div.event_icon.penalty_goal");
//		Element penalty_goal = documento.createElement("penalty_goal");
		arrSummary.add(extractSummaryPenalty(documento, typeTeam));
//		System.out.println("penal "+teamSummaryPenaltyGoal.size());
		arrSummary.add(extractSummaryYellowRedCard(documento, typeTeam));
		arrSummary.add(extractSummaryRedCard(documento, typeTeam));

		return arrSummary;
	}

	private String extractAsistencia(Document documento) {
		Element attendanceElement = documento.select("div>div>div>div.scorebox_meta>div>small").first();
		return attendanceElement.text().replaceAll(",", "");
	}

	private String extractFecha(Document documento) {
		Element datetimeElement = documento.select("span.venuetime").first();
		String strDate = datetimeElement.attr("data-venue-date");
		String strTime = datetimeElement.attr("data-venue-time");
		return strDate + " " + strTime;
	}

	private String extractFechaEpoch(Document documento) {
		Element datetimeElement = documento.select("span.venuetime").first();
		String strEpoch = datetimeElement.attr("data-venue-epoch");
		return strEpoch;
	}

	public Element extractTimeOfGame(Document documento) {
		Element TimeOfGame = documento.createElement("timeofgame");
		Element TimeOfGameElement = documento.select("div  > strong:contains(Time Of Game)").first().parents().first();
		String strTimeOfGameText = TimeOfGameElement.html();
		String[] strTimeOfGameArray = strTimeOfGameText.split("</strong>");
		String strTimeOfGame = strTimeOfGameArray[1].trim();
		String[] arrayTimeOfGame = strTimeOfGame.split(":");
		Integer timeGame = Integer.valueOf(arrayTimeOfGame[0]) * 60 + Integer.valueOf(arrayTimeOfGame[1]);
		return TimeOfGame.attr("minute", String.valueOf(timeGame));
	}

	public Element extractAttendance(Document documento) {
		Element attendance = documento.createElement("attendance");
		Element attendanceElement = documento.select("div  > strong:contains(Attendance)").first().parents().first();
		String strAttendanceText = attendanceElement.html();
		String[] strAttendanceArray = strAttendanceText.split("</strong>");
		String strAttendance = strAttendanceArray[1].replaceAll(",", "");
		return attendance.attr("person", strAttendance);
	}

	public Element extractOfficials(Document documento) {
		Element officials = documento.createElement("referees");
		Elements officialsElements = documento.select("div  > strong:contains(Officials)").first().parents().first()
				.select("a");
		for (Element element : officialsElements) {
			Element official = documento.createElement("referee");
			official.attr("url", element.attr("href"));
			official.attr("name", element.text());
			officials.appendChild(official);
		}
		return officials;
	}

	public Element extractPlayerInactive(Document documento) {
		Element inactive = documento.createElement("inactive");
		Elements inactivePlayerElements = documento.select("div  > strong:contains(Inactive)").first().parents().first()
				.select("a");
		for (Element element : inactivePlayerElements) {
			Element playerInactive = documento.createElement("player");
			playerInactive.attr("url", element.attr("href"));
			playerInactive.attr("name", element.text());
			inactive.appendChild(playerInactive);
		}
		return inactive;
	}

	public Element extractBasicBoxScoreStats(Document documento, String id) {

		Element basicBoxScoreStats = documento.createElement("basicBoxScoreStats");
		Element basicBoxScoreStatsPlayer = documento.createElement("player");

		Elements basicScoreTrElements = documento.select("table#box-" + id + "-game-basic > tbody > tr");
		Elements advancedScoreTrElements = documento.select("table#box-" + id + "-game-advanced > tbody > tr");

		int iTr = 1;
		for (Element element : basicScoreTrElements) {
			if (iTr != 6) {

				basicBoxScoreStatsPlayer = documento.createElement("player");

				Elements basicScoreTh = element.select("th");
				basicBoxScoreStatsPlayer.attr("name", basicScoreTh.get(0).text());
				basicBoxScoreStatsPlayer.attr("id", basicScoreTh.get(0).attr("data-append-csv"));
				basicBoxScoreStatsPlayer.attr("url", basicScoreTh.get(0).select("a").get(0).attr("href"));
				Elements basicScoreTd = element.select("td");
				int iTd = 1;
				String attrName = "";
				for (Element element2 : basicScoreTd) {
					attrName = "";
					switch (iTd) {
					case 1:
						attrName = "MinutesPlayed";
						break;
					case 2:
						attrName = "FieldGoals";
						break;
					case 3:
						attrName = "FieldGoalAttempts";
						break;
					case 4:
						attrName = "FieldGoalPercentage";
						break;
					case 5:
						attrName = "3PointFieldGoals";
						break;
					case 6:
						attrName = "3PointFieldGoalAttempts";
						break;
					case 7:
						attrName = "3PointFieldGoalPercentage";
						break;
					case 8:
						attrName = "FreeThrows";
						break;
					case 9:
						attrName = "FreeThrowAttempts";
						break;
					case 10:
						attrName = "FreeThrowPercentage";
						break;
					case 11:
						attrName = "OffensiveRebounds";
						break;
					case 12:
						attrName = "DefensiveRebounds";
						break;
					case 13:
						attrName = "TotalRebounds";
						break;
					case 14:
						attrName = "Assists";
						break;
					case 15:
						attrName = "Steals";
						break;
					case 16:
						attrName = "Blocks";
						break;
					case 17:
						attrName = "Turnovers";
						break;
					case 18:
						attrName = "PersonalFouls";
						break;
					case 19:
						attrName = "Points";
						break;
					case 20:
						attrName = "PlusMinus";
						break;
					default:
						attrName = "Others";
						break;
					}
					basicBoxScoreStatsPlayer.attr(attrName, element2.text());
					iTd++;
				}
				// advancedBoxScore
				Element advancedBoxScoreElement = advancedScoreTrElements.get(iTr - 1);
				Elements advancedBoxScoreElementTd = advancedBoxScoreElement.select("td");
				int iTdAdvance = 1;
				for (Element element3 : advancedBoxScoreElementTd) {
					attrName = "";
					if (iTdAdvance > 1) {
						switch (iTdAdvance) {
						case 2:
							attrName = "TrueShootingPercentage";
							break;
						case 3:
							attrName = "EffectiveFieldGoalPercentage";
							break;
						case 4:
							attrName = "3PointAttemptRate";
							break;
						case 5:
							attrName = "FreeThrowAttemptRate";
							break;
						case 6:
							attrName = "OffensiveReboundPercentage";
							break;
						case 7:
							attrName = "DefensiveReboundPercentage";
							break;
						case 8:
							attrName = "TotalReboundPercentage";
							break;
						case 9:
							attrName = "AssistPercentage";
							break;
						case 10:
							attrName = "StealPercentage";
							break;
						case 11:
							attrName = "BlockPercentage";
							break;
						case 12:
							attrName = "TurnoverPercentage";
							break;
						case 13:
							attrName = "UsagePercentage";
							break;
						case 14:
							attrName = "OffensiveRating";
							break;
						case 15:
							attrName = "DefensiveRating";
							break;
						case 16:
							attrName = "BPM";
							break;
						default:
							attrName = "Others";
							break;
						}
						basicBoxScoreStatsPlayer.attr(attrName, element3.text());

					}
					iTdAdvance++;
				}
			}

			iTr++;
			basicBoxScoreStats.appendChild(basicBoxScoreStatsPlayer);
		}

		return basicBoxScoreStats;
	}

	public ArrayList<Element> extractData(Document documento) {
		Elements scoreboxElements = documento.select("div > div > div.scorebox > div > div[itemprop]");

		Elements scoreElements = documento.select("div.scorebox > div > div.scores > div.score");

		Elements scoreElementsDiv = documento.select("div.scorebox > div > div");
		
		Elements scoreElementsDivTeams = documento.select("div.scorebox > div");

		Elements scoreboxElementsScore = documento.select("div > div > div.scorebox > div > div.scores > div.score");

		/* TEAM AWAY */
		Element teamAwayLogo = scoreboxElements.get(0).select("strong > a").first();
		Element teamAwayPhoto = scoreboxElements.get(0).select("div > img").first();
		Element teamAway = documento.createElement(TEAM).attr(TEAM_NAME, teamAwayLogo.text());
		teamAway.attr(TEAM_URL, teamAwayLogo.attr("href"));
		teamAway.attr(TEAM_LOGO, teamAwayPhoto.attr("src"));
		teamAway.attr(TEAM_RUNS, scoreboxElementsScore.get(0).text());
		teamAway.attr(TEAM_ID, this.idTeam(teamAwayPhoto.attr("src")));
		Element teamAwayWinLostDiv = scoreElementsDivTeams.get(0).select("div").get(5);
		String strTeamAwayWinLost = teamAwayWinLostDiv.text();
		String [] colStrAwayWinLost = strTeamAwayWinLost.split("-");
		teamAway.attr(TEAM_WIN, colStrAwayWinLost[0].trim());
		teamAway.attr(TEAM_LOST, colStrAwayWinLost[1].trim());

		teamAway.appendChild(extractPlayer(documento, teamAway.attr("idteam")));
		teamAway.appendChild(extractPlayerPortero(documento, teamAway.attr("idteam")));

		/* TEAM HOME */
		Element teamHomeLogo = scoreboxElements.get(1).select("strong > a").first();
		Element teamHomePhoto = scoreboxElements.get(1).select("div > img").first();
		Element teamHome = documento.createElement(TEAM).attr(TEAM_NAME, teamHomeLogo.text());
		teamHome.attr(TEAM_URL, teamHomeLogo.attr("href"));
		teamHome.attr(TEAM_LOGO, teamHomePhoto.attr("src"));
		teamHome.attr(TEAM_RUNS, scoreboxElementsScore.get(1).text());
		teamHome.attr(TEAM_ID, this.idTeam(teamHomePhoto.attr("src")));
		Element teamHomeWinLostDiv = scoreElementsDivTeams.get(1).select("div").get(5);
		String strTeamHomeWinLost = teamHomeWinLostDiv.text();
		String [] colStrHomeWinLost = strTeamHomeWinLost.split("-");
		teamHome.attr(TEAM_WIN, colStrHomeWinLost[0].trim());
		teamHome.attr(TEAM_LOST, colStrHomeWinLost[1].trim());

		teamHome.appendChild(extractPlayer(documento, teamHome.attr("idteam")));
		teamHome.appendChild(extractPlayerPortero(documento, teamHome.attr("idteam")));

//		teamHome.appendChild(extracLineScore(documento, urlTeamHome));

//		Element game =  documento.createElement("game").appendChild(teamAway);
//		game.appendChild(teamHome);

		ArrayList<Element> teams = new ArrayList<Element>();
		teams.add(teamAway);
		teams.add(teamHome);
		return teams;
	}

	private String splitStringBySeparator(String url, String stringSeparator, Integer pos) {
		String[] fragmentos = url.split(stringSeparator);
		return fragmentos[pos];
	}

	public Element extracLineScore(Document documento, String teamUrl) {
		Element lineScoreElement = null;
//		String cadena = documento.toString();
//		Elements prueba = documento.select("comment");
		Elements prueba = documento.select("div.content_grid > div > div.table_wrapper");
		String lineScore = prueba.get(0).toString().replaceAll("<!--", "").replaceAll("-->", "");
		Document doc = Jsoup.parse(lineScore);
		Elements trArray = doc.select("table > tbody > tr");

		for (Element element : trArray) {
			Elements urlElements = element.select("td");
			if (urlElements.size() > 0) {
				String url = urlElements.get(0).select("a").attr("href").toString();
				if (url.equals(teamUrl)) {

					lineScoreElement = documento.createElement("linescore");
					int contador = 0;
					for (Element element2 : urlElements) {
						if ((contador > 0) && (contador < urlElements.size() - 1)) {
							Element scoreElement = documento.createElement("score")
									.attr("time", String.valueOf(contador)).attr("points", element2.text());
							lineScoreElement.appendChild(scoreElement);
						}
						contador++;
//						System.out.println(element.toString());
					}
				}
			}
		}
//		Elements tdArray = trArray.get(2).select("td");
//		> div.overthrow.table_container > div.overthrow.table_container> table");
		return lineScoreElement;
	}

	private Element extractPlayer(Document documento, String idTeam) {
		Element players = null;
		players = documento.createElement("players");

		Elements playersListElement = documento.select("table#stats_" + idTeam + "_summary>tbody>tr");
		for (Element element : playersListElement) {
			Element player = documento.createElement("player");
//			String strPlayer =	element.select("th>a").first().html();
//			System.out.println(strPlayer);
			player.attr("player_name", element.select("th>a").first().text().trim());
			player.attr("player_url", element.select("th>a").first().attr("href").trim());
			player.attr("player_id", this.idPlayer(element.select("th>a").first().attr("href").trim()));
			player.attr("nationality", element.select("td[data-stat=nationality]>a>span").first().text().split(" ")[1]);
			player.attr("position", element.select("td[data-stat=position]").first().text());
			player.attr("minutes", element.select("td[data-stat=minutes]").first().text());
			player.attr("goals", element.select("td[data-stat=goals]").first().text());
			player.attr("assists", element.select("td[data-stat=assists]").first().text());
			player.attr("pens_made", element.select("td[data-stat=pens_made]").first().text());
			player.attr("pens_att", element.select("td[data-stat=pens_att]").first().text());
			player.attr("shots_total", element.select("td[data-stat=shots_total]").first().text());
			player.attr("shots_on_target", element.select("td[data-stat=shots_on_target]").first().text());
			player.attr("cards_yellow", element.select("td[data-stat=cards_yellow]").first().text());
			player.attr("cards_red", element.select("td[data-stat=cards_red]").first().text());
			player.attr("touches", element.select("td[data-stat=touches]").first().text());
			player.attr("pressures", element.select("td[data-stat=pressures]").first().text());
			player.attr("tackles", element.select("td[data-stat=tackles]").first().text());
			player.attr("interceptions", element.select("td[data-stat=interceptions]").first().text());
			player.attr("blocks", element.select("td[data-stat=blocks]").first().text());
			player.attr("xg", element.select("td[data-stat=xg]").first().text());
			player.attr("npxg", element.select("td[data-stat=npxg]").first().text());
			player.attr("xa", element.select("td[data-stat=xa]").first().text());
			player.attr("sca", element.select("td[data-stat=sca]").first().text());
			player.attr("gca", element.select("td[data-stat=gca]").first().text());
			player.attr("passes_completed", element.select("td[data-stat=passes_completed]").first().text());
			player.attr("passes", element.select("td[data-stat=passes]").first().text());
			player.attr("passes_pct", element.select("td[data-stat=passes_pct]").first().text());
			player.attr("passes_progressive_distance",
					element.select("td[data-stat=passes_progressive_distance]").first().text());
			player.attr("carries", element.select("td[data-stat=carries]").first().text());
			player.attr("carry_progressive_distance",
					element.select("td[data-stat=carry_progressive_distance]").first().text());
			player.attr("dribbles_completed", element.select("td[data-stat=dribbles_completed]").first().text());
			player.attr("dribbles", element.select("td[data-stat=dribbles]").first().text());
			players.appendChild(player);
		}

		return players;
	}

	private Element extractPlayerPortero(Document documento, String idTeam) {
		Element players = null;
		players = documento.createElement("goalkeepers");

		Elements playersListElement = documento.select("table#keeper_stats_" + idTeam + ">tbody>tr");
		for (Element element : playersListElement) {
			Element player = documento.createElement("player");
			player.attr("player_name", element.select("th>a").first().text().trim());
			player.attr("player_url", element.select("th>a").first().attr("href").trim());
			player.attr("player_id", this.idPlayer(element.select("th>a").first().attr("href").trim()));
			player.attr("nationality", element.select("td[data-stat=nationality]>a>span").first().text().split(" ")[1]);
			player.attr("position", "GK");
			player.attr("shots_on_target_against",
					element.select("td[data-stat=shots_on_target_against]").first().text());
			player.attr("goals_against_gk", element.select("td[data-stat=goals_against_gk]").first().text());
			player.attr("saves", element.select("td[data-stat=saves]").first().text());
//			player.attr("save_perc", 						element.select("td[data-stat=save_perc]").first().text());
			player.attr("psxg_gk", element.select("td[data-stat=psxg_gk]").first().text());
			player.attr("passes_completed_launched_gk",
					element.select("td[data-stat=passes_completed_launched_gk]").first().text());
			player.attr("passes_launched_gk", element.select("td[data-stat=passes_launched_gk]").first().text());
			player.attr("passes_pct_launched_gk",
					element.select("td[data-stat=passes_pct_launched_gk]").first().text());
			player.attr("passes_gk", element.select("td[data-stat=passes_gk]").first().text());
			player.attr("passes_throws_gk", element.select("td[data-stat=passes_throws_gk]").first().text());
			player.attr("pct_passes_launched_gk",
					element.select("td[data-stat=pct_passes_launched_gk]").first().text());
			player.attr("passes_length_avg_gk", element.select("td[data-stat=passes_length_avg_gk]").first().text());
			player.attr("goal_kicks", element.select("td[data-stat=goal_kicks]").first().text());
			player.attr("pct_goal_kicks_launched",
					element.select("td[data-stat=pct_goal_kicks_launched]").first().text());
			player.attr("goal_kick_length_avg", element.select("td[data-stat=goal_kick_length_avg]").first().text());
			player.attr("crosses_gk", element.select("td[data-stat=crosses_gk]").first().text());
			player.attr("crosses_stopped_gk", element.select("td[data-stat=crosses_stopped_gk]").first().text());
			player.attr("crosses_stopped_pct_gk",
					element.select("td[data-stat=crosses_stopped_pct_gk]").first().text());
			player.attr("def_actions_outside_pen_area_gk",
					element.select("td[data-stat=def_actions_outside_pen_area_gk]").first().text());
			player.attr("avg_distance_def_actions_gk",
					element.select("td[data-stat=avg_distance_def_actions_gk]").first().text());
			players.appendChild(player);
		}

		return players;
	}

	private String idTeam(String strSrcLogo) {
		String[] splitSrcLogo = strSrcLogo.split("/");
		return splitSrcLogo[splitSrcLogo.length - 1].replaceAll(".png", "");
	}

	private String idPlayer(String strUrlHref) {
		String[] arrStrUrlHref = strUrlHref.split("/");
		return arrStrUrlHref[3];
	}

}
