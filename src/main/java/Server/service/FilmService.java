package Server.service;

import Server.data.Film;
import Server.repository.CategoryRepository;
import Server.repository.FilmRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    @Value("${host.nextjs}")
    private String hostNextjs;

    private String parseUrl(String type, String url){
        if(type.equals("movies")){
            return hostNextjs + "/phim-le/" + url;
        }
        return hostNextjs + "/phim-bo/" + url;
    }

    public void saveFilm(Film film){
        filmRepository.save(film);
    }

    public String phimTypeLimit(String type, int limit){
        List<Object[]> results = filmRepository.phimTypeLimit(type, limit);
        JsonArray jsonArray = new JsonArray();
        for(Object[] result:results){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", (String) result[0]);
            jsonObject.addProperty("star", (Number) result[1]);
            jsonObject.addProperty("year", (Number) result[2]);
            jsonObject.addProperty("urlImage", (String) result[3]);
            jsonObject.addProperty("url", (String) result[4]);
            jsonObject.addProperty("type", (String) result[5]);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
    public String phimTypeUpdateLimit(String type, int limit){
        List<Object[]> results = filmRepository.phimTypeUpdateLimit(type, limit);
        JsonArray jsonArray = new JsonArray();
        for(Object[] result:results){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", (String) result[0]);
            jsonObject.addProperty("nameEng", (String) result[1]);
            jsonObject.addProperty("sub", (String) result[2]);
            jsonObject.addProperty("urlImage", (String) result[3]);
            jsonObject.addProperty("url", (String) result[4]);
            jsonObject.addProperty("type", (String) result[5]);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
    public String phimTypeSlideShowUpdateLimit(String type, int limit){
        List<Object[]> results = filmRepository.phimTypeSlideShowUpdateLimit(type, limit);
        JsonArray jsonArray = new JsonArray();
        for(Object[] result:results){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", (String) result[0]);
            jsonObject.addProperty("sub", (String) result[1]);
            jsonObject.addProperty("year", (Number) result[2]);
            jsonObject.addProperty("urlImage500", (String) result[3]);
            jsonObject.addProperty("url", (String) result[4]);
            jsonObject.addProperty("type", (String) result[5]);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
    public String phimCategoryLimit(String category, int limit){
        List<Object[]> results = filmRepository.phimCategoryLimit(category, limit);
        JsonArray jsonArray = new JsonArray();
        for(Object[] result:results){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", (String) result[0]);
            jsonObject.addProperty("nameEng", (String) result[1]);
            jsonObject.addProperty("sub", (String) result[2]);
            jsonObject.addProperty("urlImage", (String) result[3]);
            jsonObject.addProperty("url", (String) result[4]);
            jsonObject.addProperty("type", (String) result[5]);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
    public String phimTrendLimit(int limit){
        List<Object[]> results = filmRepository.phimTrendLimit(limit);
        JsonArray jsonArray = new JsonArray();
        for(Object[] result:results){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", (String) result[0]);
            jsonObject.addProperty("year", (Number) result[1]);
            jsonObject.addProperty("sub", (String) result[2]);
            jsonObject.addProperty("urlImage", (String) result[3]);
            jsonObject.addProperty("url", (String) result[4]);
            jsonObject.addProperty("type", (String) result[5]);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
    public String phimTypeTrendLimit(String type, int limit){
        List<Object[]> results = filmRepository.phimTypeTrendLimit(type,limit);
        JsonArray jsonArray = new JsonArray();
        for(Object[] result:results){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", (String) result[0]);
            jsonObject.addProperty("year", (Number) result[1]);
            jsonObject.addProperty("sub", (String) result[2]);
            jsonObject.addProperty("urlImage", (String) result[3]);
            jsonObject.addProperty("url", (String) result[4]);
            jsonObject.addProperty("type", (String) result[5]);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
    public Film phimTypeUrl(String type, String url){
        return filmRepository.phimTypeUrl(type, url);
    }

    public Page<Object[]> phimTypePage(String type, int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Object[]> pageObject = filmRepository.phimTypePage(type,pageable);
        return pageObject;
    }
    public Page<Object[]> phimCategoryPage(String category, int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Object[]> pageObject = filmRepository.phimCategoryPage(category,pageable);
        return pageObject;
    }
    public Page<Object[]> phimLegionPage(String legion, int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Object[]> pageObject = filmRepository.phimLegionPage(legion,pageable);
        return pageObject;
    }
    public Page<Object[]> phimYearPage(int year, int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Object[]> pageObject = filmRepository.phimYearPage(year,pageable);
        return pageObject;
    }
}
