import sys
import redis


class SignalGen:
    def __init__(self):
        self.redispool = redis.ConnectionPool(host='localhost', port=6379,password='',
                                              decode_responses=True)
        self.redis = redis.Redis(connection_pool=self.redispool)

    def do_action(self, action, scope):
        message = {'spider': action, 'scope': scope}
        self.redis.publish('notification', str(message))

    def close(self):
        self.redispool.disconnect()


if __name__ == '__main__':
    generator = SignalGen()
    arg1 = sys.argv[1]
    arg2 = sys.argv[2]
    generator.do_action(arg1, arg2)
    generator.close()
