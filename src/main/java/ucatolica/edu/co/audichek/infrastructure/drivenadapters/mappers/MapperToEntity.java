package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

public interface MapperToEntity <E, D>{
    E toEntity(D dto);
}
