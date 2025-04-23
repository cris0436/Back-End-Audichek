from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from models.Person import Person
from database.Conections import Base

class Admin(Person):
    __tablename__ = 'admins'

    id = Column(Integer, ForeignKey('persons.id'), primary_key=True)  # Reutiliza la clave primaria de Person
    notes = Column(String(100), nullable=True)

    person = relationship("Person", back_populates="admin")
