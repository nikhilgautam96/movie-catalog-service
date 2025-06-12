package nik.gautam.moviecatalogservice.resources;

import nik.gautam.moviecatalogservice.models.CatalogItem;
import nik.gautam.moviecatalogservice.models.Movie;
import nik.gautam.moviecatalogservice.models.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        /**
         * We will now consume another REST api using a REST API Client.
         * Formtunately SpringBoot provides a inbuilt Rest api client that is RestTemplate.
         */
        RestTemplate restTemplate = new RestTemplate();
        List<Rating> ratingList = Arrays.asList(
                new Rating("1234", 4),
                new Rating("1235", 5)
        );
        return ratingList.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(),
                    Movie.class);
            return new CatalogItem(movie.getName(), "Transformers", rating.getRating());
        }).collect(Collectors.toList());

//        return Collections.singletonList(
//                new CatalogItem("Optimus Prime", "Transformers", 4)
//        );

    }
}
