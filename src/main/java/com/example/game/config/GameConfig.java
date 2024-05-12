package com.example.game.config;

public class GameConfig {
  public static final int questionShowingTime = 5;
  public static final int GAME_INITIALIZED_CODE = 17;
  public static final int GAME_STARTED_CODE = 18;
  public static final int QUESTION_ENDED_CODE = 19;
  public static final int QUESTION_SHOWING_CODE = 20;
  public static final int QUESTION_STATISTICS_CODE = 21;
  public static final int GAME_RANKING_CODE = 22;
  public static final int GAME_FINAL_RANK_CODE = 23;
  public static final int CODE_LENGTH = 7;

  public static class EmptyGameStateEvent {
    public static final String BIND = "bind";
  }

  public static class LobbyStateEvent {
    public static final String GET_ACCESS_CODE = "get_access_code";
    public static final String REGISTER = "register";
    public static final String START_GAME = "start_game";
  }

  public static class QShowingStateEvent {
    public static final String SHOW_QUESTION = "show_question";
    public static final String ANSWER_QUESTION = "answer_question";
  }

  public static class QAnsweringStateEvent {
    public static final String SEND_CHOICE = "send_choice";
  }

  public static class QStatisticsStateEvent {
    public static final String SEND_RESULT = "send_result";
  }

  public static class GameRankingStateEvent {
    public static final String GET_RANKING = "get_ranking";
  }

  public static class GameEndStateEvent {
    public static final String GET_FINAL_RANK = "get_final_rank";
  }

  public static class ParamName {
    public static final String EVENT = "event";
    public static final String DATA = "data";
    public static final String NAME = "name";
  }
}
