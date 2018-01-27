package me.caosh.condition.domain.model.account;

import com.google.common.base.MoreObjects;

/**
 * 注册用户
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public class User {
    private final Integer userId;

    public User(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId != null ? userId.equals(user.userId) : user.userId == null;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(User.class).omitNullValues()
                .add("userId", userId)
                .toString();
    }
}
