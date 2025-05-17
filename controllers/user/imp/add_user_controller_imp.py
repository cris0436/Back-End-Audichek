
from sqlalchemy.orm import Session
from models.entities import Person
from user.add_user_controller import AddUserController
from models.entities import User
from models.entities import Admin
from schemas.UserSchema import AuthUser, UserCreate
from sqlalchemy.exc import IntegrityError
from exceptions.db_exceptions import handle_integrity_error
from fastapi import HTTPException
from imp import GetPersonRol
from imp.get_person_rol_imp import getPersonRol
import hashlib
from fastapi  import Depends
from models.entities import Rol 
from models.pesistency import DataBaseSession as db

class AddUserControllerImp(AddUserController):
    def __init__(self, getRol: GetPersonRol = Depends(getPersonRol()), db: Session = Depends(db.get_db())):
        self.db = db
        self.getRol = getRol
        
    def add_user(self, user_data: UserCreate):
        try:
            # Verificar si el rol existe
            rol= self.getRol.get_person_rol(user_data.rol)

            # Crear la nueva persona y asociar el rol
            new_person = Person(
                name=user_data.name,
                email=user_data.email,
                rol_id=rol.id,  # Asociar el rol con la persona
                birth_date=user_data.birth_date
            )
            self.db.add(new_person)
            self.db.commit()
            self.db.refresh(new_person)

            # Crear el nuevo usuario y asociarlo a la persona
            new_user = User(
                username=user_data.username,
                password=hashlib.sha256(user_data.password.encode()).hexdigest(),
                person_id=new_person.id ,
                ocupation=user_data.ocupation  # Asignar la ocupación como el rol
            )
            self.db.add(new_user)
            self.db.commit()
            self.db.refresh(new_user)

            # Retornar el usuario creado
            return {
                "id": new_user.id,
                "username": new_user.username,
                "ocupation": new_user.ocupation,
                "person": {
                    "name": new_person.name,
                    "email": new_person.email,
                    "ocupation": new_user.ocupation,
                    "role": rol.name,  # Incluye el nombre del rol
                    "birth_date": new_person.birth_date
                }
            }

        except IntegrityError as e:
            self.db.rollback()
            print(f"Error de integridad: {e}")
            raise HTTPException(status_code=400, detail="Error de integridad: este usuario ya existe.")
        except Exception as e:
            self.db.rollback()
            print(f"Otro error: {e}")
            raise HTTPException(status_code=500, detail=f"Error inesperado al crear el usuario: {e}")

def getAddUserControllerImp():
    return AddUserControllerImp()