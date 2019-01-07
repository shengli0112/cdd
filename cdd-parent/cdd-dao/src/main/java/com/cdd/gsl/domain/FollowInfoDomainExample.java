package com.cdd.gsl.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FollowInfoDomainExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FollowInfoDomainExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFollowIdIsNull() {
            addCriterion("follow_id is null");
            return (Criteria) this;
        }

        public Criteria andFollowIdIsNotNull() {
            addCriterion("follow_id is not null");
            return (Criteria) this;
        }

        public Criteria andFollowIdEqualTo(Long value) {
            addCriterion("follow_id =", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdNotEqualTo(Long value) {
            addCriterion("follow_id <>", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdGreaterThan(Long value) {
            addCriterion("follow_id >", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdGreaterThanOrEqualTo(Long value) {
            addCriterion("follow_id >=", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdLessThan(Long value) {
            addCriterion("follow_id <", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdLessThanOrEqualTo(Long value) {
            addCriterion("follow_id <=", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdIn(List<Long> values) {
            addCriterion("follow_id in", values, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdNotIn(List<Long> values) {
            addCriterion("follow_id not in", values, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdBetween(Long value1, Long value2) {
            addCriterion("follow_id between", value1, value2, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdNotBetween(Long value1, Long value2) {
            addCriterion("follow_id not between", value1, value2, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowTypeIsNull() {
            addCriterion("follow_type is null");
            return (Criteria) this;
        }

        public Criteria andFollowTypeIsNotNull() {
            addCriterion("follow_type is not null");
            return (Criteria) this;
        }

        public Criteria andFollowTypeEqualTo(Integer value) {
            addCriterion("follow_type =", value, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeNotEqualTo(Integer value) {
            addCriterion("follow_type <>", value, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeGreaterThan(Integer value) {
            addCriterion("follow_type >", value, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("follow_type >=", value, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeLessThan(Integer value) {
            addCriterion("follow_type <", value, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeLessThanOrEqualTo(Integer value) {
            addCriterion("follow_type <=", value, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeIn(List<Integer> values) {
            addCriterion("follow_type in", values, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeNotIn(List<Integer> values) {
            addCriterion("follow_type not in", values, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeBetween(Integer value1, Integer value2) {
            addCriterion("follow_type between", value1, value2, "followType");
            return (Criteria) this;
        }

        public Criteria andFollowTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("follow_type not between", value1, value2, "followType");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCreateTsIsNull() {
            addCriterion("create_ts is null");
            return (Criteria) this;
        }

        public Criteria andCreateTsIsNotNull() {
            addCriterion("create_ts is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTsEqualTo(Date value) {
            addCriterion("create_ts =", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotEqualTo(Date value) {
            addCriterion("create_ts <>", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsGreaterThan(Date value) {
            addCriterion("create_ts >", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsGreaterThanOrEqualTo(Date value) {
            addCriterion("create_ts >=", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsLessThan(Date value) {
            addCriterion("create_ts <", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsLessThanOrEqualTo(Date value) {
            addCriterion("create_ts <=", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsIn(List<Date> values) {
            addCriterion("create_ts in", values, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotIn(List<Date> values) {
            addCriterion("create_ts not in", values, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsBetween(Date value1, Date value2) {
            addCriterion("create_ts between", value1, value2, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotBetween(Date value1, Date value2) {
            addCriterion("create_ts not between", value1, value2, "createTs");
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