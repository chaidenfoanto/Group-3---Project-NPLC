package com.restfulnplc.nplcrestful.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.model.Team;

@Service
public class LeaderboardService {

        @Autowired
        private TeamService teamService;

        @Autowired
        private BoothgamesService boothgamesService;

        @Autowired
        private SinglematchService singlematchService;

        @Autowired
        private DuelMatchService duelMatchService;

        public Object getLeaderboard() {
                ArrayList<Object> teams = new ArrayList<Object>();
                for (Team team : teamService.getAllTeam()) {
                        int singleMatches = singlematchService.getSinglematchesByUser(team.getIdTeam()).size();
                        int duelMatches = duelMatchService.getDuelMatchesByUser(team.getIdTeam()).size();
                        int duelMatchesWin = duelMatchService.getWinningDuelMatchesByUser(team.getIdTeam()).size();
                        teams.add(
                                        Map.of(
                                                        "totalPoin", team.getTotalPoin(),
                                                        "team", Map.of(
                                                                        "namaTeam", team.getNama(),
                                                                        "idTeam", team.getIdTeam()),
                                                        "gameStats", Map.of(
                                                                        "totalGamesPlayed", singleMatches + duelMatches,
                                                                        "singleMatchPlayed", singleMatches,
                                                                        "duelMatchPlayed", duelMatches,
                                                                        "duelMatchWon", duelMatchesWin)));
                }

                return Map.of(
                                "jumlahBoothgame", boothgamesService.getGameStats(),
                                "teams", teams);
        }
}