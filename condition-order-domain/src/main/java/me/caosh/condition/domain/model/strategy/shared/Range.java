package me.caosh.condition.domain.model.strategy.shared;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.strategy.factor.CompareOperator;

/**
 * 基于{@link Comparable}的区间
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class Range<T extends Comparable<? super T>> {
    private T left;
    private T right;
    private CompareOperator leftOperator;
    private CompareOperator rightOperator;

    public Range(T left, T right, CompareOperator leftOperator, CompareOperator rightOperator) {
        Preconditions.checkArgument(right.compareTo(left) >= 0,
                "Right value must be greater than or equals left value");
        Preconditions.checkArgument(leftOperator == CompareOperator.GT || leftOperator == CompareOperator.GE,
                "Left operator must be GT or GE");
        Preconditions.checkArgument(rightOperator == CompareOperator.LT || rightOperator == CompareOperator.LE,
                "Right operator must be LT or LE");

        this.left = left;
        this.right = right;
        this.leftOperator = leftOperator;
        this.rightOperator = rightOperator;
    }

    public T getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }

    public CompareOperator getLeftOperator() {
        return leftOperator;
    }

    public CompareOperator getRightOperator() {
        return rightOperator;
    }

    public boolean isInRange(T x) {
        return leftOperator.apply(x, left) && rightOperator.apply(x, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Range<?> range = (Range<?>) o;

        if (left != null ? !left.equals(range.left) : range.left != null) {
            return false;
        }
        if (right != null ? !right.equals(range.right) : range.right != null) {
            return false;
        }
        if (leftOperator != range.leftOperator) {
            return false;
        }
        return rightOperator == range.rightOperator;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        result = 31 * result + (leftOperator != null ? leftOperator.hashCode() : 0);
        result = 31 * result + (rightOperator != null ? rightOperator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (leftOperator == CompareOperator.GT) {
            stringBuilder.append("(");
        } else {
            stringBuilder.append("[");
        }
        stringBuilder.append(left).append(", ").append(right);
        if (rightOperator == CompareOperator.LT) {
            stringBuilder.append(")");
        } else {
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }
}
