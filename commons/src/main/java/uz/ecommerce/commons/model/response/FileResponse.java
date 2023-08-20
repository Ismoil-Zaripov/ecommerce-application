package uz.ecommerce.commons.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private int id;
    private String name;
    private String path;
    private String url;
    private long size;
}
