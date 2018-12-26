package com.wan37.mysql.pojo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TMissionProgressExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TMissionProgressExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPlayeridIsNull() {
            addCriterion("playerId is null");
            return (Criteria) this;
        }

        public Criteria andPlayeridIsNotNull() {
            addCriterion("playerId is not null");
            return (Criteria) this;
        }

        public Criteria andPlayeridEqualTo(Long value) {
            addCriterion("playerId =", value, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridNotEqualTo(Long value) {
            addCriterion("playerId <>", value, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridGreaterThan(Long value) {
            addCriterion("playerId >", value, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridGreaterThanOrEqualTo(Long value) {
            addCriterion("playerId >=", value, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridLessThan(Long value) {
            addCriterion("playerId <", value, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridLessThanOrEqualTo(Long value) {
            addCriterion("playerId <=", value, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridIn(List<Long> values) {
            addCriterion("playerId in", values, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridNotIn(List<Long> values) {
            addCriterion("playerId not in", values, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridBetween(Long value1, Long value2) {
            addCriterion("playerId between", value1, value2, "playerid");
            return (Criteria) this;
        }

        public Criteria andPlayeridNotBetween(Long value1, Long value2) {
            addCriterion("playerId not between", value1, value2, "playerid");
            return (Criteria) this;
        }

        public Criteria andMissionidIsNull() {
            addCriterion("missionId is null");
            return (Criteria) this;
        }

        public Criteria andMissionidIsNotNull() {
            addCriterion("missionId is not null");
            return (Criteria) this;
        }

        public Criteria andMissionidEqualTo(Integer value) {
            addCriterion("missionId =", value, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidNotEqualTo(Integer value) {
            addCriterion("missionId <>", value, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidGreaterThan(Integer value) {
            addCriterion("missionId >", value, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidGreaterThanOrEqualTo(Integer value) {
            addCriterion("missionId >=", value, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidLessThan(Integer value) {
            addCriterion("missionId <", value, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidLessThanOrEqualTo(Integer value) {
            addCriterion("missionId <=", value, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidIn(List<Integer> values) {
            addCriterion("missionId in", values, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidNotIn(List<Integer> values) {
            addCriterion("missionId not in", values, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidBetween(Integer value1, Integer value2) {
            addCriterion("missionId between", value1, value2, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionidNotBetween(Integer value1, Integer value2) {
            addCriterion("missionId not between", value1, value2, "missionid");
            return (Criteria) this;
        }

        public Criteria andMissionStateIsNull() {
            addCriterion("mission_state is null");
            return (Criteria) this;
        }

        public Criteria andMissionStateIsNotNull() {
            addCriterion("mission_state is not null");
            return (Criteria) this;
        }

        public Criteria andMissionStateEqualTo(Integer value) {
            addCriterion("mission_state =", value, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateNotEqualTo(Integer value) {
            addCriterion("mission_state <>", value, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateGreaterThan(Integer value) {
            addCriterion("mission_state >", value, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("mission_state >=", value, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateLessThan(Integer value) {
            addCriterion("mission_state <", value, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateLessThanOrEqualTo(Integer value) {
            addCriterion("mission_state <=", value, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateIn(List<Integer> values) {
            addCriterion("mission_state in", values, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateNotIn(List<Integer> values) {
            addCriterion("mission_state not in", values, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateBetween(Integer value1, Integer value2) {
            addCriterion("mission_state between", value1, value2, "missionState");
            return (Criteria) this;
        }

        public Criteria andMissionStateNotBetween(Integer value1, Integer value2) {
            addCriterion("mission_state not between", value1, value2, "missionState");
            return (Criteria) this;
        }

        public Criteria andBegintimeIsNull() {
            addCriterion("beginTime is null");
            return (Criteria) this;
        }

        public Criteria andBegintimeIsNotNull() {
            addCriterion("beginTime is not null");
            return (Criteria) this;
        }

        public Criteria andBegintimeEqualTo(Date value) {
            addCriterion("beginTime =", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeNotEqualTo(Date value) {
            addCriterion("beginTime <>", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeGreaterThan(Date value) {
            addCriterion("beginTime >", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("beginTime >=", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeLessThan(Date value) {
            addCriterion("beginTime <", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeLessThanOrEqualTo(Date value) {
            addCriterion("beginTime <=", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeIn(List<Date> values) {
            addCriterion("beginTime in", values, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeNotIn(List<Date> values) {
            addCriterion("beginTime not in", values, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeBetween(Date value1, Date value2) {
            addCriterion("beginTime between", value1, value2, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeNotBetween(Date value1, Date value2) {
            addCriterion("beginTime not between", value1, value2, "begintime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNull() {
            addCriterion("endTime is null");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNotNull() {
            addCriterion("endTime is not null");
            return (Criteria) this;
        }

        public Criteria andEndtimeEqualTo(Date value) {
            addCriterion("endTime =", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotEqualTo(Date value) {
            addCriterion("endTime <>", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThan(Date value) {
            addCriterion("endTime >", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("endTime >=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThan(Date value) {
            addCriterion("endTime <", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThanOrEqualTo(Date value) {
            addCriterion("endTime <=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIn(List<Date> values) {
            addCriterion("endTime in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotIn(List<Date> values) {
            addCriterion("endTime not in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeBetween(Date value1, Date value2) {
            addCriterion("endTime between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotBetween(Date value1, Date value2) {
            addCriterion("endTime not between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andProgressIsNull() {
            addCriterion("progress is null");
            return (Criteria) this;
        }

        public Criteria andProgressIsNotNull() {
            addCriterion("progress is not null");
            return (Criteria) this;
        }

        public Criteria andProgressEqualTo(String value) {
            addCriterion("progress =", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotEqualTo(String value) {
            addCriterion("progress <>", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThan(String value) {
            addCriterion("progress >", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThanOrEqualTo(String value) {
            addCriterion("progress >=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThan(String value) {
            addCriterion("progress <", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThanOrEqualTo(String value) {
            addCriterion("progress <=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLike(String value) {
            addCriterion("progress like", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotLike(String value) {
            addCriterion("progress not like", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressIn(List<String> values) {
            addCriterion("progress in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotIn(List<String> values) {
            addCriterion("progress not in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressBetween(String value1, String value2) {
            addCriterion("progress between", value1, value2, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotBetween(String value1, String value2) {
            addCriterion("progress not between", value1, value2, "progress");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}