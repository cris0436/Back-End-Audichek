from sqlalchemy.orm import Session
from models import Person
from models.User import User
from models.Admin import Admin
from schemas.UserSchema import UserCreate
from sqlalchemy.exc import IntegrityError
from exceptions.db_exceptions import handle_integrity_error  # <-- asegúrate de que esto exista
from fastapi import HTTPException

from models.Rol import Rol  # <- Importamos el modelo Rol

def create_user(db: Session, user_data: UserCreate):
    try:
        # Verificar si el rol existe
        rol = db.query(Rol).filter(Rol.name == user_data.rol).first()
        if not rol:
            # Si el rol no existe, lo creamos
            new_rol = Rol(
                name=user_data.rol,
                description=user_data.rol  # Puedes ajustar la descripción según lo necesario
            )
            db.add(new_rol)
            db.commit()
            db.refresh(new_rol)
            rol = new_rol

        # Crear la nueva persona y asociar el rol
        new_person = Person(
            name=user_data.name,
            email=user_data.email,
            rol_id=rol.id,  # Asociar el rol con la persona
            birth_date=user_data.birth_date
        )
        db.add(new_person)
        db.commit()
        db.refresh(new_person)

        # Crear el nuevo usuario y asociarlo a la persona
        new_user = User(
            username=user_data.username,
            password=user_data.password,
            person_id=new_person.id  # Asociar el usuario con la persona
        )
        db.add(new_user)
        db.commit()
        db.refresh(new_user)

        # Retornar el usuario creado
        return {
            "id": new_user.id,
            "username": new_user.username,
            "person": {
                "name": new_person.name,
                "email": new_person.email,
                "role": rol.name,  # Incluye el nombre del rol
                "birth_date": new_person.birth_date
            }
        }

    except IntegrityError as e:
        db.rollback()
        print(f"Error de integridad: {e}")
        raise HTTPException(status_code=400, detail="Error de integridad: este usuario ya existe.")
    except Exception as e:
        db.rollback()
        print(f"Otro error: {e}")
        raise HTTPException(status_code=500, detail=f"Error inesperado al crear el usuario: {e}")

# Obtener un usuario por ID
def get_user(db: Session, user_id: int):
    return db.query(User).filter(User.id == user_id).first()

# Obtener todos los usuarios
def get_all_users(db: Session):
    try:
        return db.query(User).all()
    except Exception as e:
        print(f"Error al obtener usuarios: {e}")
        raise HTTPException(status_code=500, detail="Error inesperado al obtener los usuarios")
# Actualizar un usuario

def update_user(db: Session, user_id: int, updated_data: dict):
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        return None
    for key, value in updated_data.items():
        if hasattr(user, key):
            setattr(user, key, value)
    db.commit()
    db.refresh(user)
    return user

# Eliminar un usuario
def delete_user(db: Session, user_id: int):
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        return None
    db.delete(user)
    db.commit()
    return user