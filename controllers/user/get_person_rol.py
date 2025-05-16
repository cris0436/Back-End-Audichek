from sqlalchemy.orm import Session
from models.entities import Rol

class GetPersonRol:
    def get_person_rol(self ,db: Session,rol_name: str):
        rol = db.query(Rol).filter(Rol.name == rol_name).first()
        if not rol:
            # Si el rol no existe, lo creamos
            new_rol = Rol(
                name=rol_name,
                description=rol_name  # Puedes ajustar la descripción según lo necesario
            )
            db.add(new_rol)
            db.commit()
            db.refresh(new_rol)
            rol = new_rol
        return (rol)