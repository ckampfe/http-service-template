# http-service

A Boot template for a simple HTTP service

## Usage

`boot -d boot/new new -t http-service -S -n your-service-name`

## What you get

- [aero](https://github.com/juxt/aero) for config management
- [aleph](https://github.com/ztellman/aleph) for HTTP server
- [bidi](https://github.com/juxt/bidi) for routing
- [timbre](https://github.com/ptaoussanis/timbre) for logging
- [mount](https://github.com/tolitius/mount) for state management
- [ring-defaults](https://github.com/ring-clojure/ring-defaults) for sane HTTP API handling

```
clark$> boot -d boot/new new -t http-service -S -n your-example-service
Generating fresh 'boot new' http-service project.
[~/code]
clark$> tree your-example-service
your-example-service
├── build.boot
├── resources
│   ├── config.edn
│   └── dev-config.edn
├── src
│   └── your_example_service
│       ├── config.clj
│       ├── controller.clj
│       ├── core.clj
│       └── web.clj
└── test
    └── your_example_service
        └── core_test.clj

5 directories, 8 files
```

## TODO

- [x] env-based log levels
- [ ] database layer with migration support


## License

Copyright © 2018 Clark Kampfe

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
