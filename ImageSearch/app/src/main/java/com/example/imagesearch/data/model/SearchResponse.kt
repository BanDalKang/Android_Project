package com.example.imagesearch.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SearchResponse<T>(
    @SerializedName("meta") val meta: MetaResponse?,
    @SerializedName("documents") val documents: MutableList<T>?,
)

data class MetaResponse( // 응답 관련 정보
    @SerializedName("total_count") val totalCount: Int?, // 검색된 문서 수
    @SerializedName("pageable_count") val pageableCount: Int?, // total_count 중 노출 가능 문서 수
    @SerializedName("is_end") val isEnd: Boolean?, // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
)

data class ImageDocumentResponse( // 응답 결과
    @SerializedName("collection") val collection: String?, // 컬렉션
    @SerializedName("thumbnail_url") val thumbnailUrl: String?, // 미리보기 이미지 URL
    @SerializedName("image_url") val imageUrl: String?, // 이미지 URL
    @SerializedName("width") val width: Int?, // 이미지의 가로 길이
    @SerializedName("height") val height: Int?, // 이미지의 세로 길이
    @SerializedName("display_sitename") val displaySitename: String?, // 출처
    @SerializedName("doc_url") val docUrl: String?, // 문서 url
    @SerializedName("datetime") val datetime: Date?, // 문서 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
)

data class VideoDocumentResponse(
    @SerializedName("title") val title: String?, // 동영상 제목
    @SerializedName("url") val url: String?, // 동영상 링크
    @SerializedName("datetime") val datetime: Date?, // 동영상 등록일, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    @SerializedName("play_time") val playTime: Int?, // 동영상 재생시간, 초 단위
    @SerializedName("thumbnail") val thumbnail: String?, // 동영상 미리보기 URL
    @SerializedName("author") val author: String?, // 동영상 업로더
)