from pydantic import BaseModel
from datetime import date

# Nuevo esquema para la persona relacionada
class PersonData(BaseModel):
    name: str = None
    email: str = None
    role: str = None
    birth_date: date = None


# Esquema de creación de usuario
from pydantic import BaseModel
from datetime import date

class UserCreate(BaseModel):
    name: str
    email: str
    birth_date: date
    username: str
    password: str
    rol: str  # El nombre del rol que se asignará al usuario

    class Config:
        orm_mode = True


# Esquema de salida de usuario
class UserOut(BaseModel):
    id: int
    username: str
    person: PersonData  # Relación con la persona

