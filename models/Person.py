from sqlalchemy import Column, Integer, String, Date, ForeignKey
from sqlalchemy.orm import relationship
from database.connections import Base


class Person(Base):
    __tablename__ = 'persons'

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False)
    email = Column(String(30), unique=True, index=True)
    birth_date = Column(Date, nullable=False)
    rol_id = Column(Integer, ForeignKey('rols.id'))  # Relación con Rol

    rol = relationship("Rol", backref="persons")  # Relación uno-a-muchos con Rol
    users = relationship("User", back_populates="person")  # Relación uno-a-muchos con User
    admin = relationship("Admin", back_populates="person", uselist=False)  # Relación uno-a-uno con Admin
