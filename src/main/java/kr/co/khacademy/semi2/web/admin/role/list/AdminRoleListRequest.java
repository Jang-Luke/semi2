package kr.co.khacademy.semi2.web.admin.role.list;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.khacademy.semi2.model.Criteria;
import kr.co.khacademy.semi2.model.RoleSearchField;
import kr.co.khacademy.semi2.web.admin.role.AdminRoleException;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Value
public class AdminRoleListRequest {

    private static final long DEFAULT_PAGE = 1L;
    private static final long DEFAULT_LIMIT = 10L;
    private static final RoleSearchField DEFAULT_SEARCH_FIELD = RoleSearchField.NAME;
    private static final String DEFAULT_KEYWORD = "";

    long page;
    long limit;
    RoleSearchField roleSearchField;
    String keyword;

    @Builder
    private static AdminRoleListRequest of(
        String page,
        String limit,
        String roleSearchField,
        String keyword
    ) {
        long newPage = Optional.ofNullable(page)
            .map(AdminRoleListRequest::parseValidPageRange)
            .map(optionalPage -> optionalPage.orElseThrow(() -> new AdminRoleException(
                String.format("Invalid page was entered: \"%s\"", page))))
            .orElse(DEFAULT_PAGE);

        long newLimit = Optional.ofNullable(limit)
            .map(AdminRoleListRequest::parseValidLimitRange)
            .map(optionalLimit -> optionalLimit.orElseThrow(() -> new AdminRoleException(
                String.format("Invalid limit was entered: \"%s\"", limit))))
            .orElse(DEFAULT_LIMIT);

        RoleSearchField newRoleSearchField = Optional.ofNullable(roleSearchField)
            .map(RoleSearchField::of)
            .map(optionalRoleSearchField -> optionalRoleSearchField.orElseThrow(() -> new AdminRoleException(
                String.format("Invalid role search field was entered: \"%s\"", roleSearchField))))
            .orElse(DEFAULT_SEARCH_FIELD);

        String newKeyword = Optional.ofNullable(keyword)
            .map(AdminRoleListRequest::validateKeywordLength)
            .map(optionalKeyword -> optionalKeyword.orElseThrow(() -> new AdminRoleException(
                String.format("Invalid keyword was entered: \"%s\"", keyword))))
            .orElse(DEFAULT_KEYWORD);

        return new AdminRoleListRequest(
            newPage,
            newLimit,
            newRoleSearchField,
            newKeyword
        );
    }

    private static Optional<Long> parseValidPageRange(String page) {
        try {
            return Optional.of(page)
                .map(Long::valueOf)
                .filter(newPage -> 0L < newPage);
        } catch (NumberFormatException ignored) {
        }
        return Optional.empty();
    }

    private static Optional<Long> parseValidLimitRange(String limit) {
        try {
            return Optional.of(limit)
                .map(Long::valueOf)
                .filter(newLimit -> 0L < newLimit);
        } catch (NumberFormatException ignored) {
        }
        return Optional.empty();
    }

    private static Optional<String> validateKeywordLength(String keyword) {
        return Optional.of(keyword)
            .filter(newKeyword -> (2 < newKeyword.length()) && (newKeyword.length() < 256));
    }

    public static AdminRoleListRequest of(HttpServletRequest httpServletRequest) {
        String page = httpServletRequest.getParameter("page");
        String limit = httpServletRequest.getParameter("limit");
        String searchField = httpServletRequest.getParameter("search-field");
        String keyword = httpServletRequest.getParameter("keyword");

        return AdminRoleListRequest.of(
            page,
            limit,
            searchField,
            keyword
        );
    }

    public Criteria toCriteria() {
        return Criteria.builder()
            .page(page)
            .limit(limit)
            .searchField(roleSearchField.getColumn())
            .keyword(keyword)
            .build();
    }
}
