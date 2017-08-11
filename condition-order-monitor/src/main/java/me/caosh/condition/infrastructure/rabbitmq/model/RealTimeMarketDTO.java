package me.caosh.condition.infrastructure.rabbitmq.model;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by caosh on 2017/8/9.
 */
public class RealTimeMarketDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String c; // code
    private String n; // name
    private String s; // market source
    private BigDecimal tn; // total number 成交量
    private BigDecimal a; // total amount 成交
    private BigDecimal op; // open price
    private BigDecimal up; // up price
    private BigDecimal lp; // low price
    private BigDecimal pp; // previous price
    private BigDecimal p; // current price
    private BigDecimal cpp; // changes percent
    private String st; // state（汉字）：涨停、跌停、上市、停牌
    private Date dt; // trade date
    private BigDecimal sp5; // sell 5 ~ buy 5
    private BigDecimal sp4;
    private BigDecimal sp3;
    private BigDecimal sp2;
    private BigDecimal sp1;
    private BigDecimal bp1;
    private BigDecimal bp2;
    private BigDecimal bp3;
    private BigDecimal bp4;
    private BigDecimal bp5;

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public BigDecimal getTn() {
        return tn;
    }

    public void setTn(BigDecimal tn) {
        this.tn = tn;
    }

    public BigDecimal getA() {
        return a;
    }

    public void setA(BigDecimal a) {
        this.a = a;
    }

    public BigDecimal getOp() {
        return op;
    }

    public void setOp(BigDecimal op) {
        this.op = op;
    }

    public BigDecimal getUp() {
        return up;
    }

    public void setUp(BigDecimal up) {
        this.up = up;
    }

    public BigDecimal getLp() {
        return lp;
    }

    public void setLp(BigDecimal lp) {
        this.lp = lp;
    }

    public BigDecimal getPp() {
        return pp;
    }

    public void setPp(BigDecimal pp) {
        this.pp = pp;
    }

    public BigDecimal getP() {
        return p;
    }

    public void setP(BigDecimal p) {
        this.p = p;
    }

    public BigDecimal getCpp() {
        return cpp;
    }

    public void setCpp(BigDecimal cpp) {
        this.cpp = cpp;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public BigDecimal getSp5() {
        return sp5;
    }

    public void setSp5(BigDecimal sp5) {
        this.sp5 = sp5;
    }

    public BigDecimal getSp4() {
        return sp4;
    }

    public void setSp4(BigDecimal sp4) {
        this.sp4 = sp4;
    }

    public BigDecimal getSp3() {
        return sp3;
    }

    public void setSp3(BigDecimal sp3) {
        this.sp3 = sp3;
    }

    public BigDecimal getSp2() {
        return sp2;
    }

    public void setSp2(BigDecimal sp2) {
        this.sp2 = sp2;
    }

    public BigDecimal getSp1() {
        return sp1;
    }

    public void setSp1(BigDecimal sp1) {
        this.sp1 = sp1;
    }

    public BigDecimal getBp1() {
        return bp1;
    }

    public void setBp1(BigDecimal bp1) {
        this.bp1 = bp1;
    }

    public BigDecimal getBp2() {
        return bp2;
    }

    public void setBp2(BigDecimal bp2) {
        this.bp2 = bp2;
    }

    public BigDecimal getBp3() {
        return bp3;
    }

    public void setBp3(BigDecimal bp3) {
        this.bp3 = bp3;
    }

    public BigDecimal getBp4() {
        return bp4;
    }

    public void setBp4(BigDecimal bp4) {
        this.bp4 = bp4;
    }

    public BigDecimal getBp5() {
        return bp5;
    }

    public void setBp5(BigDecimal bp5) {
        this.bp5 = bp5;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(RealTimeMarketDTO.class).omitNullValues()
                .addValue(RealTimeMarketDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("c", c)
                .add("n", n)
                .add("s", s)
                .add("tn", tn)
                .add("a", a)
                .add("op", op)
                .add("up", up)
                .add("lp", lp)
                .add("pp", pp)
                .add("p", p)
                .add("cpp", cpp)
                .add("st", st)
                .add("dt", dt)
                .add("sp5", sp5)
                .add("sp4", sp4)
                .add("sp3", sp3)
                .add("sp2", sp2)
                .add("sp1", sp1)
                .add("bp1", bp1)
                .add("bp2", bp2)
                .add("bp3", bp3)
                .add("bp4", bp4)
                .add("bp5", bp5)
                .toString();
    }
}
