{{name}}

---

# Building

`$ boot build`

## Running

`$ CLJ_ENV={dev,test,prod} java -jar target/{{name}}-0.1.0-SNAPSHOT-standalone.jar`

Test this with:

`curl http://localhost:3000`

See `src/{{name}}/config.clj`, `resources/config.edn`, and `resources/dev-config.edn` for more information.

# Database migrations

### Creating a migration

```
# first, start a repl:
$ boot repl
boot.user=> (migratus.core/create {} "create-books-table")
```

This will create two files in `resources/migrations`: an `up` and a `down`, for migrating "forward" and "backward" respectively. These files are SQL, so you should edit them to look like:

```sql
-- up
CREATE TABLE widgets (id SERIAL PRIMARY KEY, name VARCHAR(255));
```

and down might look like:

```sql
-- down
DROP TABLE widgets;
```

### Running migrations

This functionality is in beta and currently hardcoded. You will have to edit `task-options!` in `build.boot` to change the target DB that your migrations run against.


To migrate:

```
$ boot migratus -c migrate
migrating all outstanding migrations
18-05-13 18:53:20 Clarks-MacBook-Pro.local INFO [migratus.core:286] - Starting migrations
18-05-13 18:53:20 Clarks-MacBook-Pro.local DEBUG [migratus.migrations:286] - Looking for migrations in #object[java.io.File 0x2c88430a /Users/clark/.boot/cache/tmp/Users/clark/code/eee/koj/-m7ldk0/migrations]
18-05-13 18:53:20 Clarks-MacBook-Pro.local INFO [migratus.core:286] - Running up for [20180513185245]
18-05-13 18:53:20 Clarks-MacBook-Pro.local INFO [migratus.core:286] - Up 20180513185245-create-widgets-table
18-05-13 18:53:20 Clarks-MacBook-Pro.local DEBUG [migratus.migration.sql:286] - found 1 up migrations
18-05-13 18:53:20 Clarks-MacBook-Pro.local DEBUG [migratus.database:286] - marking 20180513185245 complete
18-05-13 18:53:20 Clarks-MacBook-Pro.local INFO [migratus.core:286] - Ending migrations

```

To rollback:

```
$ boot migratus -c rollback
rolling back last migration
18-05-13 18:53:52 Clarks-MacBook-Pro.local INFO [migratus.core:286] - Starting migrations
18-05-13 18:53:52 Clarks-MacBook-Pro.local DEBUG [migratus.migrations:286] - Looking for migrations in #object[java.io.File 0xae95c49 /Users/clark/.boot/cache/tmp/Users/clark/code/eee/kq4/-m7ldk0/migrations]
18-05-13 18:53:52 Clarks-MacBook-Pro.local INFO [migratus.core:286] - Running down for [20180513185245]
18-05-13 18:53:52 Clarks-MacBook-Pro.local INFO [migratus.core:286] - Down 20180513185245-create-widgets-table
18-05-13 18:53:52 Clarks-MacBook-Pro.local DEBUG [migratus.migration.sql:286] - found 1 down migrations
18-05-13 18:53:52 Clarks-MacBook-Pro.local DEBUG [migratus.database:286] - marking 20180513185245 not complete
18-05-13 18:53:52 Clarks-MacBook-Pro.local INFO [migratus.core:286] - Ending migrations
```
