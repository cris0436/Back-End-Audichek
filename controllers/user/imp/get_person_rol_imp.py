from sqlalchemy.orm import Session
from models.entities import Rol
from controllers.user.get_person_rol import GetPersonRol
from fastapi import Depends
from models.persistence.DatabaseSession import DataBaseSession

db_session = DataBaseSession()

class GetPersonRolImp(GetPersonRol):
    def get_person_rol(self, rol_name: str, db: Session = Depends(db_session.get_db)):  
        rol = db.query(Rol).filter(Rol.name == rol_name).first()
        if not rol:
            # Si el rol no existe, lo creamos
            new_rol = Rol(name=rol_name, description=rol_name)
            db.add(new_rol)
            db.commit()
            db.refresh(new_rol)
            rol = new_rol
        return rol

def getPersonRol():
    return GetPersonRolImp()
