package com.mysite.sbb.music;

import com.mysite.sbb.music.dto.MusicListDto;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MusicService {

    private final MusicRepository musicRepository;

    private String extractYoutubeId(String url) {
        if (url == null || url.isEmpty()) return null;

        String[] patterns = {
                "youtube\\.com/watch\\?v=([^&?#]+)",
                "youtu\\.be/([^&?#]+)",
                "youtube\\.com/embed/([^&?#]+)",
                "youtube\\.com/v/([^&?#]+)",
                "youtube\\.com/shorts/([^&?#]+)"
        };

        for (String p : patterns) {
            Pattern pattern = Pattern.compile(p);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public String getEmbedUrl(String url) {
        String videoId = extractYoutubeId(url);
        return videoId != null ? "https://www.youtube.com/embed/" + videoId : "";
    }

    public String getThumbnailUrl(String url) {
        String videoId = extractYoutubeId(url);
        if (videoId == null) {
            return "/images/no-thumbnail.jpg";
        }
        return "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
    }

    public List<MusicListDto> getList() {
        return musicRepository.findAll().stream()
                .map(m -> new MusicListDto(
                        m.getId(),
                        m.getTitle(),
                        m.getArtist(),
                        m.getUrl(),
                        m.getContent(),
                        m.getVoter() != null ? m.getVoter().size() : 0,
                        m.getAnswerList() != null ? m.getAnswerList().size() : 0,
                        getThumbnailUrl(m.getUrl())
                ))
                .collect(Collectors.toList());
    }

    // 이 메서드가 없어서 500 에러 났음! 추가 필수
    public List<MusicListDto> getRankingListDto() {
        return musicRepository.findAll().stream()
                .sorted((m1, m2) -> Integer.compare(
                        m2.getVoter() != null ? m2.getVoter().size() : 0,
                        m1.getVoter() != null ? m1.getVoter().size() : 0
                ))
                .limit(10)
                .map(m -> new MusicListDto(
                        m.getId(),
                        m.getTitle(),
                        m.getArtist(),
                        m.getUrl(),
                        m.getContent(),
                        m.getVoter() != null ? m.getVoter().size() : 0,
                        m.getAnswerList() != null ? m.getAnswerList().size() : 0,
                        getThumbnailUrl(m.getUrl())
                ))
                .collect(Collectors.toList());
    }

    public Music getMusic(Long id) {
        return musicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("音楽が見つかりません ID: " + id));
    }

    @Transactional
    public Music create(String title, String artist, String url, String content, String category, SiteUser author) {
        Music m = new Music();
        m.setTitle(title);
        m.setArtist(artist);
        m.setUrl(url);
        m.setContent(content);
        m.setCategory(category);
        m.setAuthor(author);
        m.setCreateDate(LocalDateTime.now());
        m.setLikes(0);
        return musicRepository.save(m);
    }
}