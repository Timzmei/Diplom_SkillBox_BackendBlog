package main.service;

import main.api.response.ApiPostResponse;

public interface IPostService {
    ApiPostResponse findPaginated(int pageNo, int pageSize);
}
