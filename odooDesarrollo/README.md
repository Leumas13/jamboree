# Entorno de Desarrollo Odoo - Jamboree

Este directorio contiene el entorno de desarrollo de Odoo para el sistema Jamboree.

## Estructura de personalización

```plaintext
jamboree/odooDesarrollo/odoo/addons
├── base_location/                                # Dependencia de l10n_es_toponyms
├── base_location_geonames_import/                # Dependencia de l10n_es_toponyms
├── l10n_es_toponyms/                             # Raiz del módulo de terceros Toponimos
├── stmg_jamboree/                                # Raiz del módulo personalizado para Jamboree
│   └── controllers/                               
│   │   └── PortalEntrenamiento.py                # Controlador para gestionar accesos y resgistros del portal de entrenador
│   └── data/                                      
│   │   └── cron_jobs.xml                         # Tarea programada para calcular la categoria de jugador antes de empezar cada temporada
│   └── demo/                             
│   │   └── demo.xml                              # Datos de demo creados con la instalación del módulo
│   └── models/                                   
│   │   └── Entrenador.py                         # Modelo que representa a un empleado de tipo entrenador
│   │   └── Entrenamientos.py                     # Modelo que representa un entrenamiento
│   │   └── Jugador.py                            # Modelo que representa a un jugador de fútbol de categoría infantil
│   │   └── Sede.py                               # Modelo que recoge los entrenamientos en una localización
│   │   └── SincroContactos.py                    # Modelo encargado de sincronizar res.partner con las clases de jamboree
│   │   └── Tutor.py                              # Modelo que representa a un adulto encargado relacionado con el modelo jugador
│   └── security/                                  
│   │   └── groups.xml                            # Grupos de usuarios
│   │   └── ir.model.access.csv                   # Permisos de acceso a los modelos de jamboree
│   │   └── rules.xml                             # Reglas de acceso a registros
│   └── static/                                    
│   │   └── imgs/                                 # Recursos de tipo imagen
│   │   └── src/css/                              # Recursos de tipo css
│   └── views/                             
│   │   └── interno/                              # Vistas dedicadas a la parte interna del módulo
│   │   │   └── entrenador.xml                    # Conjunto de vistas relacionadas con el modelo entrenador
│   │   │   └── entrenamiento.xml                 # Conjunto de vistas relacionadas con el modelo entrenamiento
│   │   │   └── jugador.xml                       # Conjunto de vistas relacionadas con el modelo jugador
│   │   │   └── menus.xml                         # Conjunto de menus y acciones de la parte interna del módulo
│   │   │   └── sede.xml                          # Conjunto de vistas relacionadas con el modelo sede
│   │   │   └── sincroContacto.xml                # Personalización de la vista de res.partner
│   │   │   └── tutor.xml                         # Conjunto de vistas relacionadas con el modelo tutor
│   │   └── portal/                               # Vistas dedicadas a Portal
│   │   │   └── entrenamiento.xml                 # Vista para presentar los entrenamientos filtrados al usuario
│   │   │   └── menu.xml                          # Conjunto de menus y acciones de Portal
├── \_\_init__.py                                 
├── \_\_manifest__.py                             # Fichero de configuración del módulo
├── hooks.py                                      # Script con los métodos que deben ser lanzados inmediatamente despues de instalar el módulo
├── web_company_color/                            # Raiz de módulo de terceros para personalizar los colores de Odoo
```
