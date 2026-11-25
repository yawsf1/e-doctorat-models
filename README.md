# E-Doctorat — backend (contenu du cours)

Projet Spring Boot **sans interface graphique** : couches `controller` → `service` → `repository`, DTO + mapper, 23 entités JPA et Spring Security.

## Ce qui est implémenté

- **Partie Candidat** (éléments non commentés du TP) :
  - candidater sur 3 sujets maximum, avec chemin de document de projet de recherche optionnel (`pathFile`) ;
  - modifier ses candidatures avant la date de fermeture du calendrier.
- **Partie Professeur** (éléments non commentés du TP) :
  - proposer un sujet / thème de recherche ;
  - mettre à jour sa proposition ;
  - consulter les candidats à ses sujets.
- **23 entités** : les tables du schéma fourni sont conservées telles quelles.
- **Spring Security** : `SecurityFilterChain`, utilisateurs chargés depuis `auth_user` via `UserDetailsService`, rôles/authorities lus depuis `auth_group`, méthodes sécurisables avec `@PreAuthorize` (activation only).

## Endpoints

| Méthode | URL | Description |
|---|---|---|
| GET | `/candidats?id=1` | candidat par `id` via `@RequestParam` |
| GET | `/candidats/1` | candidat par `id` via `@PathVariable` |
| POST | `/candidats/1/candidatures?pathFile=...` | corps : `[1,2,3]` — candidater |
| PUT | `/candidats/1/candidatures?pathFile=...` | corps : `[1,2,3]` — modifier avant fermeture |
| POST | `/professeurs/1/sujets` | proposer un sujet |
| PUT | `/professeurs/1/sujets/5` | mettre à jour un sujet du professeur |
| GET | `/professeurs/1/candidats` | candidats aux sujets du professeur |

Toutes les URL sauf `/` et `/home` nécessitent une authentification.

## Choix et adaptations (hors cours uniquement là où nécessaire)

1. **Java 17** au lieu de Java 25 : seul JDK installé dans l’environnement.
2. **`@EnableMethodSecurity(prePostEnabled = true)`** remplace `@EnableGlobalMethodSecurity` du cours, supprimé dans Spring Security 6/7.
3. **Pas d’entité `Role` / table `users_roles`** : les 23 tables du schéma sont conservées ; les `auth_group` servent de rôles (`SimpleGrantedAuthority`). Le nom d’un groupe est donc l’authority (ex. `ROLE_PROFESSEUR`).
4. **HTTP Basic au lieu de `formLogin`** : conformément à « no UI », le formulaire de connexion généré par Spring Security est remplacé par l’authentification Basic. Aucun template HTML n’est ajouté.
5. **Délai de candidature** : la ligne de `directeur_pole_calendrier` utilisée est celle dont `pour = "candidature"` (valeur non précisée dans le cours, nécessaire pour la règle « avant le délai de fermeture »).
6. **Mot de passe** : suivre le cours, stocker `{bcrypt}` + hash BCrypt (aucun `PasswordEncoder` custom n’est déclaré, Spring utilise son `DelegatingPasswordEncoder` par défaut).
7. **`@Transactional`** sur les méthodes qui suppriment puis ré-enregistrent les candidatures : nécessaire pour le `@Modifying` JPA.

## Configuration

`application.properties` contient directement la connexion locale :

- URL : `jdbc:mysql://localhost:3306/edoct?useSSL=false&serverTimezone=Africa/Casablanca`
- utilisateur : `edoct`
- mot de passe : `edoct`

Notez que MySQL écoute sur le port **3306** sur cette machine (pas 3307). Hibernate crée/met à jour les tables au premier démarrage (`ddl-auto=update`).

`PhysicalNamingStrategyStandardImpl` est configuré pour conserver **exactement** les noms de colonnes du schéma du professeur (`villeDeNaissance`, `dateDeNaissance`, etc.) au lieu de la conversion snake_case par défaut de Spring Boot.

Utilisateur API local inséré dans `auth_user` (mot de passe `{bcrypt}` + hash BCrypt) :

- username : `edoct`
- password : `edoct`
- groupe : `ROLE_USER`

Appel avec authentification Basic :

```bash
curl -u edoct:edoct http://localhost:8080/candidats?id=1
```

`/` et `/home` sont publics (`HomeController` retourne `OK`) ; toutes les autres URL renvoient `401` avec `WWW-Authenticate: Basic`.

```bash
mvn -DskipTests package
mvn spring-boot:run
```
