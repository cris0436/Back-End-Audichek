from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from models.Person import Person
from database.Conections import Base

class User(Person):
    __tablename__ = 'users'

    id = Column(Integer, ForeignKey('persons.id'), primary_key=True)
    username = Column(String(100), unique=True, nullable=False)  # Especificar longitud para `username`
    password = Column(String(255), nullable=False)  # Especificar longitud para `password`
    rol = Column(String(50), nullable=False)  # Especificar longitud para `rol`

    person = relationship("Person", back_populates="user")
    audiometries = relationship("Audiometry", back_populates="user")
