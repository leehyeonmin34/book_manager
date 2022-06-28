package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
public class PublisherDto extends BaseDto{

    @NotBlank(groups = { ValidationGroups.normal.class })
    private String name;

    @Getter
    @Builder
    @AllArgsConstructor
    static public class GetResponse{

        private Long id;

        private String name;

        public GetResponse(Publisher entity){
            id = entity.getId();
            name = entity.getName();
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    static public class GetListResponse {

        @Builder.Default
        private List<GetResponse> publishers = new ArrayList<>();

        @Builder.Default
        private int total = 0;

        public GetListResponse(List<Publisher> entities) {
            this.publishers = entities.stream().map(item -> new GetResponse(item)).collect(Collectors.toList());
            this.total = publishers.size();
        }
    }


    @Getter
    @Builder
    @AllArgsConstructor
    static public class Request{
        private String name;
        private Long id;

        public PublisherDto toServiceDto(){
            return PublisherDto.builder().name(name).id(id).build();
        }

    }

}
