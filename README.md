Mison and simdjson are both high-performance JSON parsers designed to efficiently process large volumes of data.

Mison is a JSON parser developed by Microsoft Research that is tailored for analytical data processing systems. It achieves high performance by pushing down both projection and filter operators of analytical queries into the parser 1
Simdjson, on the other hand, is a JSON parser that uses commonly available SIMD instructions and microparallel algorithms to achieve high parsing speeds. It is capable of parsing JSON at gigabytes per second on commodity processors 2.

The depth-2 bitmap codec presented in this project can also provide significant performance benefits for certain use cases. By being stateless and without allocation, it can efficiently process terabytes of compressed gzip streams. This can be particularly useful for applications that need to capture and index large volumes of data. By providing a key-value index of the original file-bucket storage gzipped JSON data, the codec enables fast random access recall using tools such as gztool and jzran. Furthermore, by porting tools such as gztool into a browser-compatible WebAssembly runtime environment, it is possible to provide fast reification of the framed elements directly in the client.

This project is a simple exploration of the simd auto-vectorization potential of kotlin-common,  a very long shot to say the least, in order to enable some portable options downsrteam to storage intensive json corpii.
