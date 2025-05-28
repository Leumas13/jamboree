# Entorno de Producción Odoo - Jamboree

Este directorio contiene la configuración del entorno de producción para el sistema Odoo utilizado por la empresa Jamboree.

## Características

- Configuración estable de Odoo para uso en entorno productivo.
- Separación clara de los entornos de desarrollo y producción.
- Puede incluir mejoras de rendimiento, configuración de seguridad y otros ajustes específicos para producción.

## Requisitos

- Docker
- Docker Compose
- Servidor con acceso a red y almacenamiento persistente

## Despliegue

```bash
docker-compose -f docker-compose.yml up -d
```

**Nota:** Asegúrese de revisar las variables de entorno y los volúmenes montados antes de ejecutar en producción.

## Licencia

MIT
