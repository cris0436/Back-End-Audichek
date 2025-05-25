package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

public interface MapperToDTO<E, D> {
    D toDTO(E entity);
}
