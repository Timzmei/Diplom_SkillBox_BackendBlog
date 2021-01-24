package main.service;

import main.api.response.CalendarResponse;
import main.model.Post;
import main.model.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service // В процессе
public class CalendarService {


//    {
//        "years": [2017, 2018, 2019, 2020],
//        "posts": {
//                "2019-12-17": 56,
//                "2019-12-14": 11,
//                "2019-06-17": 1,
//                "2020-03-12": 6
//    }
//    }

    @Autowired
    private PostRepository postRepository;

    public CalendarResponse getCalendar(String year) {

        List<Post> listAllPost = postRepository.findAllPosts();

        SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatForYear = new SimpleDateFormat("yyyy");

        Set<String> years = new TreeSet<>();
        Map<String, Integer> posts = new TreeMap<>();

        String currentYear = formatForYear.format(new Date());

        if(year.equals("none")){
            year = currentYear;
        }




        for (Post p:listAllPost
             ) {
            String yearDate = formatForYear.format(p.getTime());
            years.add(yearDate);
            String date = formatForDate.format(p.getTime());


            if(yearDate.equals(year)) {
                if (posts.containsKey(date)) {
                    posts.put(date, posts.get(date) + 1);
                } else {
                    posts.put(date, 1);
                }
            }

        }


        return new CalendarResponse(years, posts);
    }
}
